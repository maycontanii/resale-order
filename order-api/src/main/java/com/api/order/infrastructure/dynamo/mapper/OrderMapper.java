package com.api.order.infrastructure.dynamo.mapper;

import com.api.order.core.domain.Order;
import com.api.order.infrastructure.dynamo.entity.OrderEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static OrderEntity toEntity(Order order) throws JsonProcessingException {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setIdResale(order.getIdResale());
        entity.setStatus(order.getStatus().name());
        entity.setItemsOrder(toEntityItemOrder(order.getItemOrder()));
        return entity;
    }

    public static Order toDomain(OrderEntity entity) throws JsonProcessingException {
        return new Order(entity.getId(), toDomainItemOrder(entity.getItemsOrder()), Order.Status.valueOf(entity.getStatus()));
    }

    private static List<Order.ItemOrder> toDomainItemOrder(String itemsEntityOrder) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(itemsEntityOrder, mapper.getTypeFactory().constructCollectionType(List.class, Order.ItemOrder.class));
    }

    private static String toEntityItemOrder(List<Order.ItemOrder> itemsOrder) throws JsonProcessingException {
        List<OrderEntity.ItemOrder> itemsEntityOrders = new ArrayList<>();

        for (Order.ItemOrder orderItem : itemsOrder) {
            itemsEntityOrders.add(new OrderEntity.ItemOrder(orderItem.getId(), orderItem.getQuantity()));
        }

        return new ObjectMapper().writeValueAsString(itemsEntityOrders);
    }
}
