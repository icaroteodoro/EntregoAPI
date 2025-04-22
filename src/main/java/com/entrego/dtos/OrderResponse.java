package com.entrego.dtos;

import com.entrego.domain.Address;
import com.entrego.domain.ItemOrder;
import com.entrego.domain.Product;
import com.entrego.enums.OrderStatus;
import com.entrego.enums.PaymentMethod;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(String id, String clientName, String numberOrder, Double total, LocalDateTime deliveryTime, OrderStatus status, Address address, PaymentMethod paymentMethod, List<ItemOrder> items) {
}
