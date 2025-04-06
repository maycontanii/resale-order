package com.api.order.infrastructure.dynamo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.net.URI;

@Slf4j
@Configuration
public class DynamoConfig {

    @Value("${aws.dynamo.host}")
    private String endpoint;

    @Value("${aws.region}")
    private String region;

    @Bean
    public DynamoDbEnhancedClient dynamoDbClient() {
        log.info("Configuring DynamoDbClient");

        try {
            DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                    .endpointOverride(URI.create(endpoint))
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create("test", "test")
                    ))
                    .build();

            if (dynamoDbClient.listTables().tableNames().stream().noneMatch(item -> item.contains("Order"))) {
                log.info("Running migrations...");

                createTableOrder(dynamoDbClient);

                log.info("Finished migrations");
            }

            return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
        } catch (Exception e) {
            log.error("Connection failed to DynamoDbClient");
            throw new RuntimeException(e);
        }
    }

    private void createTableOrder(DynamoDbClient dynamoDbClient) {
        CreateTableRequest request = CreateTableRequest.builder()
                .tableName("Order")
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .keySchema(KeySchemaElement.builder()
                        .attributeName("id")
                        .keyType(KeyType.HASH)
                        .build()
                )
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName("id")
                        .attributeType(ScalarAttributeType.S)
                        .build()
                )
                .build();

        createTable(dynamoDbClient, request);
    }

    private static void createTable(DynamoDbClient dynamoDbClient, CreateTableRequest request) {
        try {
            dynamoDbClient.createTable(request);
            log.info("Create table " + request.tableName());
        } catch (Exception e) {
            log.error("Create table item failed " + request.tableName());
            throw new RuntimeException(e);
        }
    }

}
