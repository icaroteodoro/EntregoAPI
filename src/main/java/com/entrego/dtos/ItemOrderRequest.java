package com.entrego.dtos;

import java.util.List;

public record ItemOrderRequest(String productId, int quantity, List<String> options) {
}
