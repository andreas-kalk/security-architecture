package com.kalk.security.server.entity;

import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Field;

public abstract class Auditable implements UuidEntity, Persistable<UUID> {

    @CreatedBy
    @Field("owner")
    private String creator;

    public String getCreator() {
        return creator;
    }

    @Override
    public boolean isNew() {
        return creator == null;
    }
}
