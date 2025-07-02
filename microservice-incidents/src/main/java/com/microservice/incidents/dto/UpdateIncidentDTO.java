package com.microservice.incidents.dto;

import com.microservice.incidents.entities.IncidentPriority;
import com.microservice.incidents.entities.IncidentStatus;
import com.microservice.incidents.entities.IncidentType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateIncidentDTO(
        IncidentType type ,
        String description,
        /*@NotNull
        IncidentPriority priority,*/
        @NotNull
        BigDecimal latitude,
        @NotNull
        BigDecimal longitude,
        @NotNull
        IncidentStatus status
) {
}
