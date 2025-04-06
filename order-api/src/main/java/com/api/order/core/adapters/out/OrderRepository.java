package com.api.order.core.adapters.out;

import com.api.order.core.domain.Order;
import com.api.order.core.ports.order.OrderOutputPort;
import com.api.order.infrastructure.dynamo.entity.OrderEntity;
import com.api.order.infrastructure.dynamo.mapper.OrderMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.ListTopicsResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.Topic;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class OrderRepository implements OrderOutputPort {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final SnsClient snsClient;

    public static String TOPIC_NAME = "order_topic";

    private DynamoDbTable<OrderEntity> getDynamoDbTable() {
        return dynamoDbEnhancedClient.table("Order", TableSchema.fromBean(OrderEntity.class));
    }

    @Override
    public String save(OrderEntity order) {
        getDynamoDbTable().putItem(order);
        return order.getId();
    }

    @Override
    public Optional<Order> findById(String id) throws JsonProcessingException {
        OrderEntity orderEntity = getDynamoDbTable().getItem(r -> r.key(k -> k.partitionValue(id)));

        if (Objects.isNull(orderEntity)) return Optional.empty();

        return Optional.of(OrderMapper.toDomain(orderEntity));
    }

    @Override
    public void postOnSns(String payload) {
        String topicArn = getTopicArnByName(TOPIC_NAME);
        if (Objects.isNull(topicArn)) throw new RuntimeException("Topic SNS not found " + TOPIC_NAME);

        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .message(payload)
                .build();

        PublishResponse response = snsClient.publish(publishRequest);
        log.info("Order published: " + response.messageId());
    }

    private String getTopicArnByName(String topicName) {
        ListTopicsResponse listTopicsResponse = snsClient.listTopics();
        List<Topic> topics = listTopicsResponse.topics();
        return topics.stream()
                .map(Topic::topicArn)
                .filter(arn -> arn.endsWith(":" + topicName))
                .findFirst()
                .orElse(null);
    }

}
