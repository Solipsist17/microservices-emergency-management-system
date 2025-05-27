package com.microservice.patrols.dto;

import java.time.LocalDateTime;

public record PatrolAssignmentDTO(
        Long id,
        Long userId,
        String userName, // para enriquecer la respuesta hacia el cliente (la entidad PatrolAssignment no la tiene)
        String shift,
        LocalDateTime createdAt
) {
}
