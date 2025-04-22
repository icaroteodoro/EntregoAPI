package com.entrego.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entrego.domain.Product;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, String> {
    List<Product> findProductsByStoreEmail(String email);
    void deleteAllByProductCategoryId(String id);
}
