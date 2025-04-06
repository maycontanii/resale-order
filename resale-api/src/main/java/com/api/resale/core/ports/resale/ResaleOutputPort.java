package com.api.resale.core.ports.resale;

import com.api.resale.core.domain.Resale;

import java.util.Optional;

public interface ResaleOutputPort {

    void save(Resale resale);

    Optional<Resale> get(String id);
}
