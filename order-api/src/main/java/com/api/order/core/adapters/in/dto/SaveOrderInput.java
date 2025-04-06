package com.api.order.core.adapters.in.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveOrderInput {

    private String idResale;
    private List<Item> items;

    @Data
    public static class Item {
        private String id;
        private int quantity;
    }
}
