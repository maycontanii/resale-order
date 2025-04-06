package com.api.resale.infrastructure.dynamo;

import com.api.resale.core.domain.Item;
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
import java.util.*;

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

            if (dynamoDbClient.listTables().tableNames().isEmpty()) {
                log.info("Running migrations...");

                createTableResale(dynamoDbClient);
                createTableItem(dynamoDbClient);
                insertItems(dynamoDbClient);

                log.info("Finished migrations");
            }

            return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
        } catch (Exception e) {
            log.error("Connection failed to DynamoDbClient");
            throw new RuntimeException(e);
        }
    }

    private void createTableResale(DynamoDbClient dynamoDbClient) {
        CreateTableRequest request = CreateTableRequest.builder()
                .tableName("Resale")
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

    private void createTableItem(DynamoDbClient dynamoDbClient) {
        CreateTableRequest request = CreateTableRequest.builder()
                .tableName("Item")
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

    private void insertItems(DynamoDbClient dynamoDbClient) {
        List<Item> items = new ArrayList<>();

        items.add(new Item(UUID.randomUUID().toString(), "external-uuid-pepsi", "Pepsi"));
        items.add(new Item(UUID.randomUUID().toString(), "external-uuid-sukita", "Sukita"));
        items.add(new Item(UUID.randomUUID().toString(), "external-uuid-brahma", "Brahma"));
        items.add(new Item(UUID.randomUUID().toString(), "external-uuid-skol", "Skol"));
        items.add(new Item(UUID.randomUUID().toString(), "external-uuid-corona", "Corona"));

        for (Item item : items) {
            Map<String, AttributeValue> itemMap = new HashMap<>();

            itemMap.put("id", AttributeValue.builder().s(item.getId()).build());
            itemMap.put("idExternal", AttributeValue.builder().s(item.getIdExternal()).build());
            itemMap.put("title", AttributeValue.builder().s(item.getTitle()).build());

            PutItemRequest putItemRequest = PutItemRequest.builder()
                    .tableName("Item")
                    .item(itemMap)
                    .build();

            dynamoDbClient.putItem(putItemRequest);
        }

        log.info("Insert items");
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
