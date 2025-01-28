package com.kalk.security.server.mapper;

import java.util.Optional;

import com.kalk.security.server.controller.Protocol;
import com.kalk.security.server.entity.ProductGroup;

public final class ProductGroupMapper {

    private ProductGroupMapper() {

    }

    public static Protocol.ProductGroup toDto(ProductGroup entity) {
        return new Protocol.ProductGroup(entity.getId(), entity.getName());
    }

    public static ProductGroup toEntity(final Protocol.ProductGroup dto) {
        final ProductGroup entity = new ProductGroup(dto.name());
        Optional.ofNullable(dto.id()).ifPresent(entity::setId);
        return entity;
    }
}
