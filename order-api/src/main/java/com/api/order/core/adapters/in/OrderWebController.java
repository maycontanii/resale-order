package com.api.order.core.adapters.in;

import com.api.order.core.adapters.in.dto.SaveOrderInput;
import com.api.order.core.domain.Order;
import com.api.order.core.ports.order.OrderInputPort;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderWebController {

    private final OrderInputPort orderInputPort;

    @PostMapping({"/", ""})
    public String save(@RequestBody SaveOrderInput input) {
        return orderInputPort.save(input);
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable String id) {
        return orderInputPort.get(id);
    }
}
