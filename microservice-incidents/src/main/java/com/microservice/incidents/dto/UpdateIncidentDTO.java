package com.microservice.incidents.dto;

import com.microservice.incidents.entities.IncidentPriority;
import com.microservice.incidents.entities.IncidentStatus;
import com.microservice.incidents.entities.IncidentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateIncidentDTO(
        IncidentType type ,
        String description,
        @NotNull
        IncidentPriority priority,
        @NotBlank
        String latitude,
        @NotBlank
        String longitude,
        @NotNull
        IncidentStatus status
) {
}
