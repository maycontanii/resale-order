package com.api.resale.core.ports.item;

import com.api.resale.core.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemOutputPort {

    Item save(Item item);

    Optional<Item> get(String id);

    List<Item> get();
}
