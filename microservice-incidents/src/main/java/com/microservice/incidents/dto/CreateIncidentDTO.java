package com.microservice.incidents.dto;


import com.microservice.incidents.entities.IncidentAssignment;
import com.microservice.incidents.entities.IncidentPriority;
import com.microservice.incidents.entities.IncidentStatus;
import com.microservice.incidents.entities.IncidentType;
import jakarta.validation.constraints.*;


public record CreateIncidentDTO(

        IncidentType type ,

        @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
        String description,

        @NotNull
        IncidentPriority priority,

        @NotNull
        IncidentStatus status,

        @NotBlank(message = "La latitud es obligatoria")
        @Pattern(
                regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$",
                message = "Latitud inválida. Debe estar entre -90 y 90"
        )
        String latitude,

        @NotBlank(message = "La longitud es obligatoria")
        @Pattern(
                regexp = "^[-+]?((1[0-7]\\d)|(\\d{1,2}))(\\.\\d+)?|180(\\.0+)?$",
                message = "Longitud inválida. Debe estar entre -180 y 180"
        )
        String longitude,

        @NotNull
        Long reportedBy
) {

}
