package com.microservice.incidents.dto;

import com.microservice.incidents.entities.IncidentAssignment;

import java.time.LocalDateTime;

public record IncidentAssignmentResponseDTO(Long id, LocalDateTime acceptedAt, LocalDateTime completedAt,
                                            Long incidentId, Long userId, Long patrolId) {
    public IncidentAssignmentResponseDTO(IncidentAssignment incidentAssignment) {
        this(incidentAssignment.getId(), incidentAssignment.getAcceptedAt(), incidentAssignment.getCompletedAt(),
                incidentAssignment.getIncident().getId(), incidentAssignment.getUserId(), incidentAssignment.getPatrolId());
    }
}
