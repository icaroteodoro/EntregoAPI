package com.entrego.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.entity.Enterprise;
import com.entrego.entity.Product;
import com.entrego.entity.User;


public record RequestDTO(User user, Enterprise enterprise, List<Product> products, LocalDateTime createAt, LocalDateTime updatedAt) {

}
