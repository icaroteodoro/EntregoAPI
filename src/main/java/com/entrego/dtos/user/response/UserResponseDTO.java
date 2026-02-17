package com.entrego.dtos.user.response;

import com.entrego.domain.User;

public record UserResponseDTO(String id, String firstName, String lastName, String email, String role, String cell, String document) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getAccount().getEmail(), user.getAccount().getRole(), user.getCell(), user.getDocument());
    }
}
