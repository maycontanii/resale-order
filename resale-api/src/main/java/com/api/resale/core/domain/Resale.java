package com.api.resale.core.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Resale {
    private String id;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private String telefone;
    private String nomeContato;
    private String enderecoEntrega;
    private String createdAt;

    public Resale() {
    }

    public Resale(String id, String cnpj, String razaoSocial, String nomeFantasia, String email, String telefone, String nomeContato, String enderecoEntrega, String createdAt) {
        this.id = id;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.email = email;
        this.telefone = telefone;
        this.nomeContato = nomeContato;
        this.enderecoEntrega = enderecoEntrega;
        this.createdAt = createdAt;
    }

    public Resale(String cnpj, String razaoSocial, String nomeFantasia, String email, String telefone, String nomeContato, String enderecoEntrega) {
        this.id = UUID.randomUUID().toString();
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.email = email;
        this.telefone = telefone;
        this.nomeContato = nomeContato;
        this.enderecoEntrega = enderecoEntrega;
        this.createdAt = LocalDateTime.now().toString();
    }
}
