package com.entrego.dtos;


import java.math.BigDecimal;

public record ProductDTO(String name, BigDecimal price, String productCategoryId, int discount, String storeId) {

}
