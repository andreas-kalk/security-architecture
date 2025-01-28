package com.kalk.security.server.entity;

import java.util.UUID;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class ProductGroup extends Auditable {

    @Id
    private UUID id;

    @Field("name")
    private String name;

    public ProductGroup() {
    }

    public ProductGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
