package com.microservice.incidents.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum IncidentType {
    ROBO,
    VANDALISMO,
    ACOSO,
    VIOLENCIA_DOMESTICA,
    OTRO;

    @JsonCreator
    public static IncidentType from (String value) {
        if(value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return IncidentType.valueOf(value.toUpperCase());

        }catch (IllegalArgumentException e){
            throw new RuntimeException("Invalid Incident Type: "+ value);
        }
    }
}
