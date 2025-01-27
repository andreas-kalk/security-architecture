package com.kalk.security.server.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.kalk.security.server.entity.Product;
import com.kalk.security.server.mapper.ProductMapper;
import com.kalk.security.server.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService productService) {
        this.service = productService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCTS_READ')")
    public List<Protocol.Product> products() {
        return service.get().stream().map(ProductMapper::toDto).toList();
    }

    @GetMapping("/{product_id}")
    @PreAuthorize("hasPermission(#productId, 'PRODUCT', T(com.kalk.security.server.security.Action).READ)")
    public ResponseEntity<Protocol.Product> productById(@PathVariable("product_id") String productId) {
        Optional<Product> entity = service.get(UUID.fromString(productId));
        return entity
                .map(product -> ResponseEntity.ok(ProductMapper.toDto(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    @PreAuthorize("hasAuthority('PRODUCTS_CREATE')")
    public Protocol.Product create(Protocol.Product product) {
        return ProductMapper.toDto(service.save(ProductMapper.toEntity(product)));
    }

    @PostMapping
    @PreAuthorize("hasPermission(#product.id(), 'PRODUCT', T(com.kalk.security.server.security.Action).UPDATE)")
    public Protocol.Product update(Protocol.Product product) {
        return ProductMapper.toDto(service.save(ProductMapper.toEntity(product)));
    }

    @DeleteMapping
    @PreAuthorize("hasPermission(#productId, 'PRODUCT', T(com.kalk.security.server.security.Action).DELETE)")
    public ResponseEntity<Void> delete(String productId) {
        service.delete(UUID.fromString(productId));
        return ResponseEntity.ok().build();
    }
}
