package com.microservice.incidents.entities;

import com.microservice.incidents.dto.CreateIncidentDTO;
import com.microservice.incidents.dto.IncidentAssignmentDTO;
import com.microservice.incidents.dto.UpdateIncidentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "incidents")
@AllArgsConstructor
@NoArgsConstructor
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private IncidentType type;
    private String description;
    private IncidentPriority priority;
    @Column(name="location_latitude")
    private String latitude;
    @Column(name="location_longitude")
    private String longitude;
    private IncidentStatus status;
    @Column(name="reported_by")
    private Long reportedBy;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Transient
    private Set<IncidentAssignmentDTO> incidentAssigment;

    public Incident(CreateIncidentDTO dto){
        this.type= dto.type();
        this.description = dto.description();
        this.priority = dto.priority();
        this.status = dto.status();
        this.latitude = dto.latitude();
        this.longitude = dto.longitude();
        this.reportedBy = dto.reportedBy();
    }

    public void update(UpdateIncidentDTO dto){
        if (dto.type() != null){
            this.type = dto.type();
        }

        if (dto.description() != null){
            this.description = dto.description();
        }

        if (dto.priority() != null){
            this.priority = dto.priority();
        }

        if (dto.latitude() != null){
            this.latitude = dto.latitude();
        }
        if (dto.longitude() != null){
            this.longitude = dto.longitude();
        }
        if (dto.status() != null){
            this.status = dto.status();
        }
    }
}
