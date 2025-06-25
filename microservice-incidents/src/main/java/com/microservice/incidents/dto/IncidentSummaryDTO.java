package com.microservice.incidents.dto;

import com.microservice.incidents.entities.*;

import java.time.LocalDateTime;

public record IncidentSummaryDTO(Long id, IncidentType type, String description,  String latitude, String longitude,
                                 IncidentStatus incidentStatus, LocalDateTime createdAt, Long reportedBy)
{
    public IncidentSummaryDTO(Incident incident){
        this(incident.getId(), incident.getType(), incident.getDescription(), incident.getLatitude(), incident.getLongitude(), incident.getStatus(),
                incident.getCreatedAt(), incident.getReportedBy());
    }
}
