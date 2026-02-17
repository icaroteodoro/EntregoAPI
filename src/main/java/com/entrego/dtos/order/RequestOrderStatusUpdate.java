package com.entrego.dtos.order;

import com.entrego.enums.OrderStatus;

public record RequestOrderStatusUpdate(String orderId, OrderStatus status) {
}
