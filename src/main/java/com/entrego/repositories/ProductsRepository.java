package com.entrego.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entrego.domain.Product;

public interface ProductsRepository extends JpaRepository<Product, String> {

}
