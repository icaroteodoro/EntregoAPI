package com.entrego.dtos;

import com.entrego.domain.Order;
import com.entrego.domain.Product;

import java.time.LocalDateTime;
import java.util.List;



public record RegisterStoreRequestDTO(String name, String document, String email, String password,
                                      String description, AddressDTO address, List<Order> orders, List<Product> products,
                                      LocalDateTime createdAt, LocalDateTime updateAt) {

}
