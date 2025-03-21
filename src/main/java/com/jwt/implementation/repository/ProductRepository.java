package com.jwt.implementation.repository;

import com.jwt.implementation.model.Product;
import com.jwt.implementation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByUser(String email);

}
