package com.entrego.dtos;

import java.util.List;

import com.entrego.domain.Product;


public record OrderDTO(String userId, String storeId, List<Product> products) {

}
