package com.entrego.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entrego.entity.Product;

public interface ProductsRepository extends JpaRepository<Product, String> {

}
