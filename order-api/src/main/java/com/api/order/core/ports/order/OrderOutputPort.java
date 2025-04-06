package com.api.order.core.ports.order;

import com.api.order.core.domain.Order;
import com.api.order.infrastructure.dynamo.entity.OrderEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface OrderOutputPort {

    String save(OrderEntity order);

    Optional<Order> findById(String id) throws JsonProcessingException;

    void postOnSns(String payload);
}
