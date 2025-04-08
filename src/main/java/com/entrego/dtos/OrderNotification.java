package com.entrego.dtos;

import com.entrego.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotification {
    private String orderId;
    private String numberOrder;
    private String clientName;
    private String storeId;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private double total;
} 