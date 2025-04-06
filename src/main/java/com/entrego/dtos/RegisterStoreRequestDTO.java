package com.entrego.dtos;

import com.entrego.domain.Order;
import com.entrego.domain.Product;
import com.entrego.enums.StoreCategoryEnum;
import com.entrego.enums.StoreStatus;

import java.time.LocalDateTime;
import java.util.List;



public record RegisterStoreRequestDTO(String name, String document, String email, String password,
                                      String description, AddressDTO address, List<Order> orders, List<Product> products, StoreCategoryEnum category,
                                      LocalDateTime createdAt, LocalDateTime updateAt) {

}
