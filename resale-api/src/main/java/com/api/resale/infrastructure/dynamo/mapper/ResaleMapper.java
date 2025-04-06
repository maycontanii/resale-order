package com.api.resale.infrastructure.dynamo.mapper;

import com.api.resale.core.domain.Resale;
import com.api.resale.infrastructure.dynamo.entity.ResaleEntity;

public class ResaleMapper {

    public static ResaleEntity toEntity(Resale resale) {
        ResaleEntity entity = new ResaleEntity();
        entity.setId(resale.getId());
        entity.setCnpj(resale.getCnpj());
        entity.setRazaoSocial(resale.getRazaoSocial());
        entity.setNomeFantasia(resale.getNomeFantasia());
        entity.setEmail(resale.getEmail());
        entity.setTelefone(resale.getTelefone());
        entity.setNomeContato(resale.getNomeContato());
        entity.setEnderecoEntrega(resale.getEnderecoEntrega());
        entity.setCreatedAt(resale.getCreatedAt());

        return entity;
    }

    public static Resale toDomain(ResaleEntity entity) {
        return new Resale(entity.getId(), entity.getCnpj(), entity.getRazaoSocial(),
                entity.getNomeFantasia(), entity.getEmail(), entity.getTelefone(),
                entity.getNomeContato(), entity.getEnderecoEntrega(), entity.getCreatedAt()
        );
    }
}
