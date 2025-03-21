package com.jwt.implementation.controller;

import com.jwt.implementation.model.Product;
import com.jwt.implementation.model.User;
import com.jwt.implementation.repository.ProductRepository;
import com.jwt.implementation.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JwtUtilService jwtUtilService; // Inject JwtUtilService

    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody Product product, @RequestHeader("Authorization") String token) {
        try {
            // Get user details from token
            User user = jwtUtilService.extractUserFromToken(token);

            // Set the user in the product and save it
            product.setUser(user.getEmail());
            Product savedProduct = productRepository.save(product);

            return ResponseEntity.ok("Product saved successfully with ID: " + savedProduct.getProductId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/get-product")
    public ResponseEntity<List<Product>> getAllProducts(@RequestHeader("Authorization") String token) {
        try {
            User user = jwtUtilService.extractUserFromToken(token);
            String email = user.getEmail();

            List<Product> products = productRepository.findByUser(email);

            if (products == null || products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

}
