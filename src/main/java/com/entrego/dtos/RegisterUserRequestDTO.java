package com.entrego.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.domain.Order;

public record RegisterUserRequestDTO(String firstName, String lastName, String email, String password, String cell, String document, LocalDateTime createdAt, LocalDateTime updatedAt, List<AddressDTO> address, List<Order> orders) {

}
