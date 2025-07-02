package com.microservice.incidents.dto;

import jakarta.validation.constraints.NotNull;

public record CreateIncidentAssignmentDTO(
        @NotNull Long incidentId,
        @NotNull Long userId,
        Long patrolId // puede ser nulo
        ) {
}
