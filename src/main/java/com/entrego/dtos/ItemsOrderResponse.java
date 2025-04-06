package com.entrego.dtos;

import com.entrego.domain.Product;

public record ItemsOrderResponse(String name, Double price, int quantity ) {
}
