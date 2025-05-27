package com.microservice.patrolassignments.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "patrol_assignments")
@AllArgsConstructor
@NoArgsConstructor
public class PatrolAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; // proveniente de usuario service (no usamos la entidad completa)
    private Long patrolId; // proveniente de patrol service (no usamos la entidad completa)
    private String shift;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
