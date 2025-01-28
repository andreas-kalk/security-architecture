package com.kalk.security.server.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.kalk.security.server.entity.Product;
import com.kalk.security.server.repo.ProductRepo;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepo repo;

    private final ProductGroupService groupService;

    public ProductService(ProductRepo repo, ProductGroupService groupService) {
        this.repo = repo;
        this.groupService = groupService;
    }

    public Product save(Product product) {
        if (product.getProductGroup() == null) {
            product.setProductGroup(groupService.getRoot());
        }

        return repo.save(product);
    }

    public List<Product> get() {
        return repo.findAll();
    }

    public Optional<Product> get(UUID id) {
        return repo.findById(id);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
