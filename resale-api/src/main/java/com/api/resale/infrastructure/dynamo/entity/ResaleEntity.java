package com.api.resale.infrastructure.dynamo.entity;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
public class ResaleEntity {

    private String id;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private String telefone;
    private String nomeContato;
    private String enderecoEntrega;
    private String createdAt;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

}
