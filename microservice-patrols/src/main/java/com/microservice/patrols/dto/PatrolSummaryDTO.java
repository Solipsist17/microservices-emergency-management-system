package com.microservice.patrols.dto;

import com.microservice.patrols.entities.Patrol;

public record PatrolSummaryDTO(Long id, String name) {
    public PatrolSummaryDTO(Patrol patrol) {
        this(patrol.getId(), patrol.getName());
    }
}
