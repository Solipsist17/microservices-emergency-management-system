package com.microservice.patrols.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePatrolDTO(@NotBlank String name) {
}
