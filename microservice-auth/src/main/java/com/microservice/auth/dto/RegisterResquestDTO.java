package com.microservice.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterResquestDTO(
        @NotBlank
        String dni,
        @NotBlank
        String email,
        @NotBlank
        String phoneNumber,
        @NotBlank
        String password,
        @NotBlank
        String role) {
}
