package com.microservice.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDTO(String dni, String email, String phoneNumber, String password, String role) {
}
