package com.entrego.dtos;


import com.entrego.domain.ProductCategory;

public record ProductDTO(String name, Double price, String productCategoryId, int discount, String storeId) {

}
