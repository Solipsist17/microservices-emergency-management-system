package com.microservice.incidents.entities;

import java.time.LocalDateTime;


public class Incident {
    private Long id;
    private IncidentType type;
    private String description;
    private String audio;
    private String imagen;
    private String video;
    private LocalDateTime createdAt = LocalDateTime.now();

}
