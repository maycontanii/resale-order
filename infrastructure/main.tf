terraform {
  required_version = ">= 1.0.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }
}

provider "aws" {
  region                      = "us-east-1"
  access_key                  = "test"
  secret_key                  = "test"
  s3_use_path_style           = true
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true
  endpoints {
    sns = "http://localstack:4566"
    sqs = "http://localstack:4566"
  }
}

resource "aws_sns_topic" "order_topic" {
  name = "order_topic"
}

resource "aws_sqs_queue" "resale_order_preparer" {
  name = "resale_order_preparer"
}

resource "aws_sqs_queue" "resale_order_processor" {
  name = "resale_order_processor"
}

resource "aws_sns_topic_subscription" "sqs_subscription" {
  topic_arn = aws_sns_topic.order_topic.arn
  protocol  = "sqs"
  endpoint  = aws_sqs_queue.resale_order_preparer.arn
}

resource "aws_sqs_queue_policy" "resale_order_preparer_queue_policy" {
  queue_url = aws_sqs_queue.resale_order_preparer.id
  policy    = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": "*",
      "Action": "SQS:SendMessage",
      "Resource": "${aws_sqs_queue.resale_order_preparer.arn}",
      "Condition": {
        "ArnEquals": {
          "aws:SourceArn": "${aws_sns_topic.order_topic.arn}"
        }
      }
    }
  ]
}
EOF
}

resource "aws_sqs_queue_policy" "resale_order_processor_queue_policy" {
  queue_url = aws_sqs_queue.resale_order_processor.id
  policy    = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": "*",
      "Action": "SQS:SendMessage",
      "Resource": "${aws_sqs_queue.resale_order_processor.arn}"
    }
  ]
}
EOF
}

resource "aws_iam_role" "lambda_exec" {
  name = "lambda-exec-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "lambda.amazonaws.com"
      }
    }]
  })
}

resource "aws_lambda_function" "resale_order_preparer_lambda" {
  function_name = "resaleOrderPreparerLambda"
  handler       = "index.handler"
  runtime       = "nodejs18.x"
  role          = aws_iam_role.lambda_exec.arn
  filename      = "${path.module}/lambda/function.zip"
  source_code_hash = filebase64sha256("${path.module}/lambda/function.zip")
}