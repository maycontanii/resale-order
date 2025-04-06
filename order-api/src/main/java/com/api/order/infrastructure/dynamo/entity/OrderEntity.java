package com.api.order.infrastructure.dynamo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
public class OrderEntity {

    private String id;
    private String idResale;
    private String itemsOrder;
    private String status;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @Data
    @AllArgsConstructor
    public static class ItemOrder {
        private String id;
        private int quantity;
    }

}
