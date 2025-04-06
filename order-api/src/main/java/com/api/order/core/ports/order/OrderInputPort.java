package com.api.order.core.ports.order;

import com.api.order.core.adapters.in.dto.SaveOrderInput;
import com.api.order.core.domain.Order;

public interface OrderInputPort {
    Order get(String id);

    String save(SaveOrderInput input);
}
