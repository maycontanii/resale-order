package com.api.resale.application;

import com.api.resale.core.adapter.in.dto.SaveResalePayload;
import com.api.resale.core.domain.Resale;
import com.api.resale.core.ports.resale.ResaleInputPort;
import com.api.resale.core.ports.resale.ResaleOutputPort;
import com.api.resale.infrastructure.dynamo.entity.ResaleEntity;
import com.api.resale.infrastructure.exceptions.APICustomException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ResaleService implements ResaleInputPort {

    private final ResaleOutputPort resaleOutputPort;

    @Override
    public String save(SaveResalePayload saveResalePayload) {
        Resale resale = getResaleFromPayload(saveResalePayload);

        resaleOutputPort.save(resale);

        return resale.getId();
    }

    @Override
    public Resale get(String id) {
        return resaleOutputPort.get(id).orElseThrow(() -> new APICustomException("Resale not found"));
    }

    private static Resale getResaleFromPayload(SaveResalePayload p) {
        return new Resale(p.getCnpj(), p.getRazaoSocial(), p.getNomeFantasia(), p.getEmail(), p.getTelefone(), p.getNomeContato(), p.getEnderecoEntrega());
    }
}
