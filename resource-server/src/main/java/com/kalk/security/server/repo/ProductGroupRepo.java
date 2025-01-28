package com.kalk.security.server.repo;

import java.util.UUID;

import com.kalk.security.server.entity.ProductGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductGroupRepo extends MongoRepository<ProductGroup, UUID> {

}
