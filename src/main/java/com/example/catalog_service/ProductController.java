package com.example.catalog_service;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ProductController {

    @GetMapping("/api/products")
    public List<Product> listProducts() {
        return List.of(
                new Product(1, "Java Notebook", 19_900),
                new Product(2, "USB Cable", 29_900),

                new Product(3, "Desk Lamp", 799)

        );
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {

        for (Product product : listProducts()) {
            if (product.id() == id) {
                return ResponseEntity.ok(product);
            }
        }

        return ResponseEntity.notFound().build();

    }

}
