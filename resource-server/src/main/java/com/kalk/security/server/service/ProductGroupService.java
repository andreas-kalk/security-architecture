package com.kalk.security.server.service;

import java.util.Optional;
import java.util.UUID;

import com.kalk.security.server.entity.ProductGroup;
import com.kalk.security.server.repo.ProductGroupRepo;
import org.springframework.stereotype.Service;

@Service
public class ProductGroupService {

    private static final UUID ROOT_ID = UUID.fromString("930e7b2b-01a9-4b47-ac6b-1d97288f0b47");

    private final ProductGroupRepo repo;

    public ProductGroupService(ProductGroupRepo repo) {
        this.repo = repo;
    }

    public ProductGroup getRoot() {
        return repo.findById(ROOT_ID).orElseGet(() -> {
            ProductGroup root = new ProductGroup("ROOT");
            root.setId(ROOT_ID);
            return repo.save(root);
        });
    }

    public Optional<ProductGroup> get(UUID id) {
        return repo.findById(id);
    }
}
