package com.entrego.dtos.auth;

public record LoginUserResponseDTO(String name, String token, String refreshToken) {
}
