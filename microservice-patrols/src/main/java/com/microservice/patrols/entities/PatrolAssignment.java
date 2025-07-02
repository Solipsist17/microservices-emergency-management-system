package com.microservice.patrols.entities;

import com.microservice.patrols.dto.CreatePatrolAssignmentDTO;
import com.microservice.patrols.dto.UpdatePatrolAssignmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "patrol_assignments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "patrol_id", "shift"}))
@AllArgsConstructor
@NoArgsConstructor
public class PatrolAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId; // proveniente de usuario service (no usamos la entidad completa)
    //private Long patrolId; // proveniente de patrol service (no usamos la entidad completa)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patrol_id")
    private Patrol patrol;

    @Column(nullable = false)
    private String shift;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public PatrolAssignment(CreatePatrolAssignmentDTO dto) {
        this.userId = dto.userId();
        this.patrol = new Patrol(dto.patrolId());
        this.shift = dto.shift();
    }

    public void update(UpdatePatrolAssignmentDTO dto) {
        if (dto.userId() != null) {
            this.userId = dto.userId();
        }
        if (dto.patrolId() != null) {
            this.patrol = new Patrol(dto.patrolId());
        }
        if (dto.shift() != null) {
            this.shift = dto.shift();
        }
    }
}
