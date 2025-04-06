package com.api.order.application;

import com.api.order.core.adapters.in.dto.SaveOrderInput;
import com.api.order.core.domain.Order;
import com.api.order.core.ports.ResaleOutputPort;
import com.api.order.core.ports.ItemOutputPort;
import com.api.order.core.ports.order.OrderInputPort;
import com.api.order.core.ports.order.OrderOutputPort;
import com.api.order.infrastructure.dynamo.mapper.OrderMapper;
import com.api.order.infrastructure.exceptions.APICustomException;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService implements OrderInputPort {

    private final OrderOutputPort orderOutputPort;
    private final ItemOutputPort itemOutputPort;
    private final ResaleOutputPort resaleOutputPort;

    @Override
    public String save(SaveOrderInput input) {
        try {
            validateResaleExists(input.getIdResale());

            List<Order.ItemOrder> itemsOrder = new ArrayList<>();

            input.getItems().forEach(item -> {
                validateItemExists(item);
                itemsOrder.add(new Order.ItemOrder(item.getId(), item.getQuantity()));
            });

            Order order = new Order(input.getIdResale(), itemsOrder, Order.Status.RECEIVED);

            String orderPersistedId = orderOutputPort.save(OrderMapper.toEntity(order));

            orderOutputPort.postOnSns(order.getIdResale());

            return orderPersistedId;
        } catch (RuntimeException e) {
            throw new APICustomException(e.getMessage());
        } catch (Exception e) {
            log.error("Error saving order", e);
            throw new APICustomException();
        }
    }

    @Override
    public Order get(String id) {
        try {
            return orderOutputPort.findById(id).orElseThrow(() -> new APICustomException("Order not found"));
        } catch (APICustomException e) {
            throw new APICustomException("Order not found");
        } catch (Exception e) {
            throw new APICustomException();
        }
    }

    private void validateResaleExists(String idResale) {
        try {
            Object resale = resaleOutputPort.get(idResale);
            if (resale == null) throw new RuntimeException("Resale not found: " + idResale);
        } catch (FeignException e) {
            throw new RuntimeException("Resale not found: " + idResale);
        }
    }

    private void validateItemExists(SaveOrderInput.Item item) {
        try {
            Object itemSaved = itemOutputPort.get(item.getId());
            if (itemSaved == null) throw new APICustomException("Item not found: " + item.getId());
        } catch (FeignException e) {
            throw new RuntimeException("Item not found: " + item.getId());
        }
    }
}
