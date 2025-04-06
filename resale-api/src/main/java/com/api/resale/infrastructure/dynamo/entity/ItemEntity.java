package com.api.resale.infrastructure.dynamo.entity;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Getter
@Setter
@DynamoDbBean
public class ItemEntity {

    private String id;
    private String idExternal;
    private String title;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

}
