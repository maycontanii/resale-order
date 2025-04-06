package com.api.order.core.ports;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resale-api", url = "${resale_api.host}")
public interface ResaleOutputPort {

    @GetMapping("/resales/{id}")
    Object get(@PathVariable("id") String id);
}
