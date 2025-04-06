package com.api.resale.core.adapter.out;

import com.api.resale.core.domain.Resale;
import com.api.resale.core.ports.resale.ResaleOutputPort;
import com.api.resale.infrastructure.dynamo.entity.ResaleEntity;
import com.api.resale.infrastructure.dynamo.mapper.ResaleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ResaleRepository implements ResaleOutputPort {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private DynamoDbTable<ResaleEntity> getDynamoDbTable() {
        return dynamoDbEnhancedClient.table("Resale", TableSchema.fromBean(ResaleEntity.class));
    }

    @Override
    public void save(Resale resale) {
        getDynamoDbTable().putItem(ResaleMapper.toEntity(resale));
    }

    @Override
    public Optional<Resale> get(String id) {
        ResaleEntity resaleEntity = getDynamoDbTable().getItem(r -> r.key(k -> k.partitionValue(id)));

        if (Objects.isNull(resaleEntity)) return Optional.empty();

        return Optional.of(ResaleMapper.toDomain(resaleEntity));
    }
}
