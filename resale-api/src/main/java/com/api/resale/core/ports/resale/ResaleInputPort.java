package com.api.resale.core.ports.resale;

import com.api.resale.core.adapter.in.dto.SaveResalePayload;
import com.api.resale.core.domain.Resale;

public interface ResaleInputPort {

    String save(SaveResalePayload payload);

    Resale get(String id);
}
