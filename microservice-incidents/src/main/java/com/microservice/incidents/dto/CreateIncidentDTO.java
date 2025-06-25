package com.microservice.incidents.dto;


import com.microservice.incidents.entities.IncidentAssignment;
import com.microservice.incidents.entities.IncidentPriority;
import com.microservice.incidents.entities.IncidentStatus;
import com.microservice.incidents.entities.IncidentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CreateIncidentDTO(

        IncidentType type ,
        String description,
        @NotNull
        IncidentPriority priority,
        @NotNull
        IncidentStatus status,
        @NotBlank
        String latitude,
        @NotBlank
        String longitude,
        @NotNull
        Long reportedBy
) {

}
