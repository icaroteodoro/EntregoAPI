package com.entrego.dtos.order;

import com.entrego.domain.Product;

public record ItemsOrderResponse(String name, Double price, int quantity ) {
}
