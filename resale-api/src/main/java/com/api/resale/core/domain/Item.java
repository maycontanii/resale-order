package com.api.resale.core.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Item {
    private String id;
    private String idExternal;
    private String title;

    public Item() {
    }

    public Item(String idExternal, String title) {
        this.id = UUID.randomUUID().toString();
        this.idExternal = idExternal;
        this.title = title;
    }

    public Item(String id, String idExternal, String title) {
        this.id = id;
        this.idExternal = idExternal;
        this.title = title;
    }
}
