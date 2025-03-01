package com.entrego.dtos;

import java.util.List;

import com.entrego.entity.Enterprise;
import com.entrego.entity.Product;
import com.entrego.entity.User;


public record RequestDTO(String userId, String enterpriseId, List<Product> products) {

}
