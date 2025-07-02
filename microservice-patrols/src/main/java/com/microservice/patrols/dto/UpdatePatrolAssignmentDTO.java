package com.microservice.patrols.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePatrolAssignmentDTO(
        @NotNull
        Long userId,
        @NotNull
        Long patrolId,
        @NotBlank
        String shift) {
}
