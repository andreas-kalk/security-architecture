package com.kalk.security.server.mapper;

import java.util.Optional;

import com.kalk.security.server.controller.Protocol;
import com.kalk.security.server.entity.Product;

public final class ProductMapper {

    private ProductMapper() {

    }

    public static Protocol.Product toDto(Product entity) {
        return new Protocol.Product(entity.getId(), entity.getName(), entity.getCreator());
    }

    public static Product toEntity(final Protocol.Product dto) {
        final Product entity = new Product();
        Optional.ofNullable(dto.id()).ifPresent(entity::setId);
        entity.setName(dto.name());
        return entity;
    }
}
