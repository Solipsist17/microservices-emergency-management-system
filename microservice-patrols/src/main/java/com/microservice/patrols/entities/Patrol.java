package com.microservice.patrols.entities;

import com.microservice.patrols.dto.PatrolAssignmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "patrols")
@AllArgsConstructor
@NoArgsConstructor
public class Patrol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // sirve para conocer los policías que han sido asignados a dicha patrulla
    @Transient
    private Set<PatrolAssignmentDTO> patrolAssignments;
}
