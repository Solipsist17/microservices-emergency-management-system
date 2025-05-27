package com.microservice.users.dto;

import com.microservice.users.entities.Role;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateUserDTO(
    @NotBlank
    @Pattern(regexp = "\\d{8}", message = "El número de dni debe contener 8 dígitos")
    String dni,
    String name, // puede ser null para ciudadanos no para policías
    String lastName, // puede ser null para ciudadanos no para policías
    @NotBlank
    @Email(message = "Debe proporcionar un email válido")
    String email,
    @NotBlank
    @Pattern(regexp = "\\d{9}", message = "El número de teléfono debe contener 9 dígitos")
    String phoneNumber,
    @NotBlank
    String password,
    /*
    @NotNull
    @PastOrPresent
    LocalDateTime createdAt,
    */
    @NotNull
    Role role) {
}
