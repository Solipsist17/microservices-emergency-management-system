package com.microservice.incidents.dto;

import java.util.Date;

public record IncidentAssignmentDTO(
        Long id,
        Date acceptedAt,
        Date completedAt,
        Long incidentId,
        Long userId,
        Long patrolId
) {
}
