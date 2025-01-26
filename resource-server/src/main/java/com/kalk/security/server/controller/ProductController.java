package com.kalk.security.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/products")
    public String products() {
        return "All products";
    }

    @GetMapping("/products/{product_id}")
    public String productById(@PathVariable("product_id") int productId) {
        return "Product with id " + productId + " found";
    }
}
