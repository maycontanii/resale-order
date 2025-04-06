package com.api.resale.infrastructure.dynamo.mapper;

import com.api.resale.core.domain.Item;
import com.api.resale.infrastructure.dynamo.entity.ItemEntity;

public class ItemMapper {

    public static ItemEntity toEntity(Item item) {
        ItemEntity entity = new ItemEntity();
        entity.setId(item.getId());
        entity.setIdExternal(item.getIdExternal());
        entity.setTitle(item.getTitle());
        return entity;
    }

    public static Item toDomain(ItemEntity entity) {
        return new Item(entity.getId(), entity.getIdExternal(), entity.getTitle());
    }
}
