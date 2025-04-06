package com.api.order.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
public class Order {
    private String id;
    private String idResale;
    private Status status;
    private List<ItemOrder> itemOrder;

    public Order(String idResale, List<ItemOrder> itemOrder, Status status) {
        this.id = UUID.randomUUID().toString();
        this.idResale = idResale;
        this.itemOrder = itemOrder;
        this.status = status;
    }

    public Order(String id, String idResale, List<ItemOrder> itemOrder, Status status) {
        this.id = id;
        this.idResale = idResale;
        this.itemOrder = itemOrder;
        this.status = status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemOrder {
        private String id;
        private int quantity;
    }

    public enum Status {
        RECEIVED,
        PREPARING,
        PROCESSING_ERROR,
        PROCESSED
    }
}
