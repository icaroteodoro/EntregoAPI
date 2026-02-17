package com.entrego.dtos.order;

import com.entrego.domain.OrderAddress;
import com.entrego.domain.ItemOrder;
import com.entrego.domain.Product;
import com.entrego.enums.OrderStatus;
import com.entrego.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(String id, String clientName, String numberOrder, BigDecimal total, LocalDateTime deliveryTime, OrderStatus status, OrderAddress address, PaymentMethod paymentMethod, List<ItemOrder> items) {
}
