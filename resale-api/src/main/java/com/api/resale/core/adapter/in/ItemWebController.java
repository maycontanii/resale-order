package com.api.resale.core.adapter.in;

import com.api.resale.core.domain.Item;
import com.api.resale.core.ports.item.ItemInputPort;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemWebController {

    private final ItemInputPort itemInputPort;

    @GetMapping({"/", ""})
    public List<Item> get() {
        return itemInputPort.get();
    }

    @GetMapping("/{id}")
    public Item get(@PathVariable String id) {
        return itemInputPort.get(id);
    }
}
