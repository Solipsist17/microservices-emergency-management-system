package com.microservice.patrols.dto;

import com.microservice.patrols.entities.PatrolAssignment;
import java.time.LocalDateTime;

public record PatrolAssignmentSummaryDTO(Long id, Long userId, Long patrolId, String shift, LocalDateTime createdAt) {
    public PatrolAssignmentSummaryDTO(PatrolAssignment patrolAssignment) {
        this(patrolAssignment.getId(), patrolAssignment.getUserId(), patrolAssignment.getPatrol().getId(),
                patrolAssignment.getShift(), patrolAssignment.getCreatedAt());
    }
}
