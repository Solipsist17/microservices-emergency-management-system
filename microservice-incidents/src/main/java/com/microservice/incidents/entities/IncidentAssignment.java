package com.microservice.incidents.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date accepted_at;
    private Date completed_at;
    private Long incident_id;
    private Long user_id;
    private Long patrol_id;
}
