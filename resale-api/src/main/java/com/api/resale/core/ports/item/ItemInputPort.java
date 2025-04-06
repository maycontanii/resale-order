package com.api.resale.core.ports.item;


import com.api.resale.core.domain.Item;

import java.util.List;

public interface ItemInputPort {
    Item get(String id);
    List<Item> get();
}
