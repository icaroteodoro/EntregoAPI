package com.entrego.repositories;

import com.entrego.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {
    List<ProductCategory> findProductCategoriesByStoreEmail(String email);
}
