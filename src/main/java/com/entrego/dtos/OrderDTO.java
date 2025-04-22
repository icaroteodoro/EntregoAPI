package com.entrego.dtos;

import java.util.List;

import com.entrego.domain.Product;
import com.entrego.enums.OrderStatus;
import com.entrego.enums.PaymentMethod;


public record OrderDTO(String userId, String storeId, List<ItemOrderRequest> items, PaymentMethod paymentMethod) {

}
