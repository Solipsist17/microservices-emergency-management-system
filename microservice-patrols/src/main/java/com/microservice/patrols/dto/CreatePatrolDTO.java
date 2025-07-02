package com.microservice.patrols.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePatrolDTO(
        @NotBlank String name) {
}
