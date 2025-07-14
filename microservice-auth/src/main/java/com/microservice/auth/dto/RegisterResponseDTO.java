package com.microservice.auth.dto;

public record RegisterResponseDTO(
        Long id,
        String dni,
        String email,
        String phoneNumber,
        String password,
        String role) {
}
