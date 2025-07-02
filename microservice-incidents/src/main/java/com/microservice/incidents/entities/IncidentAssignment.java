package com.microservice.incidents.entities;

import com.microservice.incidents.dto.CreateIncidentAssignmentDTO;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "incident_assignment")
@AllArgsConstructor
@NoArgsConstructor
public class IncidentAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt = LocalDateTime.now();
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_id", nullable = false)
    private Incident incident;

    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "patrol_id", nullable = true)
    private Long patrolId;

    public IncidentAssignment(CreateIncidentAssignmentDTO dto) {
        this.incident = new Incident(dto.incidentId());
        this.userId = dto.userId();
        this.patrolId = dto.patrolId();
    }
}
