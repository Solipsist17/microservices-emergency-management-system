package com.microservice.users.dto;

import com.microservice.users.entities.Role;
import com.microservice.users.entities.User;

import java.time.LocalDateTime;

// name y lastName pueden ser nulos en user ciudadano
public record UserSummaryDTO(Long id, String dni, String name, String lastName, String email, String phoneNumber,
                             String password, LocalDateTime createdAt, Role role) {
    public UserSummaryDTO(User user) {
        this(user.getId(), user.getDni(), user.getName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(),
                user.getPassword(), user.getCreatedAt(), user.getRole());
    }
}
