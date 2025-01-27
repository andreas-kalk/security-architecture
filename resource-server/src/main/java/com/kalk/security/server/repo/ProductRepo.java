package com.kalk.security.server.repo;

import java.util.UUID;

import com.kalk.security.server.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, UUID> {

}
