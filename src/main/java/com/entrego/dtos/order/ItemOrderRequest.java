package com.entrego.dtos.order;

import java.util.List;

public record ItemOrderRequest(String productId, int quantity, List<String> options) {
}
