package com.microservice.patrols.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(Long id, String dni, String name, String lastName, String email, String phoneNumber,
                              String password, LocalDateTime createdAt) {
}
