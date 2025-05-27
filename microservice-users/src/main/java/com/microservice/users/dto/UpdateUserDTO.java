package com.microservice.users.dto;

import com.microservice.users.entities.Role;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateUserDTO(String dni, String name, String lastName, String email,
                            String phoneNumber, String password, Role role) {
}
