package com.microservice.incidents.entities;

import com.microservice.incidents.dto.CreateIncidentDTO;
import com.microservice.incidents.dto.IncidentAssignmentDTO;
import com.microservice.incidents.dto.UpdateIncidentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private IncidentStatus status = IncidentStatus.PENDING;
    //private IncidentPriority priority;
    @Column(name="location_latitude", precision = 9, scale = 6)
    private BigDecimal latitude; // de -90 a +90
    @Column(name="location_longitude", precision = 10, scale = 6)
    private BigDecimal longitude; // de -180 a +180
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name="reported_by")
    private Long reportedBy;

    // no es necesario declararlo, solo si se necesita acceder desde Incident a sus Incident Assignment correspondientes
    @OneToMany(mappedBy = "incident", fetch = FetchType.LAZY)
    private List<IncidentAssignment> incidentAssignments = new ArrayList<>();

    /*@Transient
    private Set<IncidentAssignmentDTO> incidentAssigment;*/

    public Incident(CreateIncidentDTO dto){
        this.type= dto.type();
        this.description = dto.description();
        //this.status = dto.status();
        //this.priority = dto.priority();
        this.latitude = dto.latitude();
        this.longitude = dto.longitude();
        //this.reportedBy = dto.reportedBy();
    }

    public Incident(Long id) {
        this.id = id;
    }

    public void update(UpdateIncidentDTO dto){
        if (dto.type() != null){
            this.type = dto.type();
        }

        if (dto.description() != null){
            this.description = dto.description();
        }

        if (dto.status() != null){
            this.status = dto.status();
        }

        if (dto.latitude() != null){
            this.latitude = dto.latitude();
        }

        if (dto.longitude() != null){
            this.longitude = dto.longitude();
        }

    }
}
