package com.microservice.incidents.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(Long id, String dni, String name, String lastName, String email, String phoneNumber,
                              String password, LocalDateTime createdAt, String role) {

    /*public UserResponseDTO(User user) {
        this(user.getId(), user.getDni(), user.getName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(),
                user.getPassword(), user.getCreatedAt(), user.getRole());
    }*/

}
