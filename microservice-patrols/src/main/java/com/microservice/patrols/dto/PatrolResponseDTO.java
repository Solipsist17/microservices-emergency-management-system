package com.microservice.patrols.dto;

import com.microservice.patrols.entities.Patrol;

public record PatrolResponseDTO(Long id, String name) {
    public PatrolResponseDTO(Patrol patrol) {
        this(patrol.getId(), patrol.getName());
    }
}
