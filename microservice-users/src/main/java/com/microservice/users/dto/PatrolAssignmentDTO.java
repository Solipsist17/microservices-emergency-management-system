package com.microservice.users.dto;

import java.time.LocalDateTime;

public record PatrolAssignmentDTO(
        Long id,
        Long patrolId,
        String patrolName, // para enriquecer la respuesta hacia el cliente (la entidad PatrolAssignment no la tiene)
        String shift,
        LocalDateTime createdAt
) {
}
