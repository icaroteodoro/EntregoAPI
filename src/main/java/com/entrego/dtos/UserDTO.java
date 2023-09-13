package com.entrego.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.entity.Request;

public record UserDTO(String firstName, String lastName,String email, String cell, String document, LocalDateTime createdAt, LocalDateTime updatedAt, List<AddressDTO> address, List<Request> requests) {

}
