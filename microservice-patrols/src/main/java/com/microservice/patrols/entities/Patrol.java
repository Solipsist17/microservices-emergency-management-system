package com.microservice.patrols.entities;

import com.microservice.patrols.dto.CreatePatrolDTO;
import com.microservice.patrols.dto.PatrolAssignmentDTO;
import com.microservice.patrols.dto.UpdatePatrolDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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

    // no es necesario declararlo, solo si se necesita acceder desde Patrol a sus PatrolAssignment correspondientes
    @OneToMany(mappedBy = "patrol", fetch = FetchType.LAZY/*, cascade = CascadeType.ALL*/)
    private List<PatrolAssignment> patrolAssignments = new ArrayList<>();

    public Patrol(CreatePatrolDTO dto) {
        this.name = dto.name();
    }

    public Patrol(Long id) {
        this.id = id;
    }

    public void update(UpdatePatrolDTO dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }
    }

    // sirve para conocer las asignaciones de la patrulla
    // ¿Necesario en microservicios?
    /*
    @Transient
    private Set<PatrolAssignmentDTO> patrolAssignments;
    */
}
