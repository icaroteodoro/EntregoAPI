package com.entrego.dtos;


import com.entrego.entity.Enterprise;

public record ProductDTO(String name, Double price, int discount, Enterprise enterprise) {

}
