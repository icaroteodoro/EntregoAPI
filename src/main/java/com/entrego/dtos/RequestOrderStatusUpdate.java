package com.entrego.dtos;

import com.entrego.enums.OrderStatus;

public record RequestOrderStatusUpdate(String orderId, OrderStatus status) {
}
