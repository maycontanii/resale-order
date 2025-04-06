package com.api.resale.application;

import com.api.resale.core.ports.item.ItemOutputPort;
import com.api.resale.core.domain.Item;
import com.api.resale.core.ports.item.ItemInputPort;
import com.api.resale.infrastructure.exceptions.APICustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService implements ItemInputPort {

    private final ItemOutputPort itemOutputPort;

    @Override
    public Item get(String id) {
        return itemOutputPort.get(id).orElseThrow(() -> new APICustomException("Item not found"));
    }

    @Override
    public List<Item> get() {
        return itemOutputPort.get();
    }
}
