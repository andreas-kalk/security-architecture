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

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    public Product save(Product product) {
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
