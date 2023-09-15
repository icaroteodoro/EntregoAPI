package com.entrego.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.entity.Product;
import com.entrego.entity.Request;

public record EnterpriseDTO(String name, String document, String email, 
		String description, AddressDTO address, List<Request> requests, List<Product> products, 
		LocalDateTime createdAt, LocalDateTime updateAt) {

}
