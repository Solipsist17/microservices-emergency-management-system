package com.microservice.incidents.dto;

import com.microservice.incidents.entities.Incident;
import com.microservice.incidents.entities.IncidentStatus;
import com.microservice.incidents.entities.IncidentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IncidentResponseDTO(Long id, IncidentType type, String description, BigDecimal latitude, BigDecimal longitude,
                                  IncidentStatus incidentStatus, LocalDateTime createdAt, Long reportedBy) {

    public IncidentResponseDTO(Incident incident){
        this(incident.getId(), incident.getType(), incident.getDescription(), incident.getLatitude(),
                incident.getLongitude(), incident.getStatus(), incident.getCreatedAt(), incident.getReportedBy());
    }
}
