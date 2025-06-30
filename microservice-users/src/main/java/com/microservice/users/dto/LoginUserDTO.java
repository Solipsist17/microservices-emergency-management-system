package com.microservice.users.dto;

import com.microservice.users.entities.Role;

public record LoginUserDTO(
    Long id,
    String email,
    String password,
    Role role
) {
}
