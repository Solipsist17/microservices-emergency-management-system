package com.microservice.users.entities;

import com.microservice.users.dto.CreateUserDTO;
import com.microservice.users.dto.PatrolAssignmentDTO;
import com.microservice.users.dto.UpdateUserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dni; // DNI (8 dígitos)
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String password;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private Role role; // CITIZEN o POLICE

    // sirve para conocer las patrullas a las que ha sido asignado el policía
    @Transient
    private Set<PatrolAssignmentDTO> patrolAssignments;

    public User(CreateUserDTO dto) {
        this.dni = dto.dni();
        this.name = dto.name();
        this.lastName = dto.lastName();
        this.email = dto.email();
        this.phoneNumber = dto.phoneNumber();
        this.password = dto.password();
        this.role = dto.role();
    }

    public void update(UpdateUserDTO dto) {
        if (dto.dni() != null) {
            this.dni = dto.dni();
        }
        if (dto.name() != null) {
            this.name = dto.name();
        }
        if (dto.lastName() != null) {
            this.lastName = dto.lastName();
        }
        if (dto.email() != null) {
            this.email = dto.email();
        }
        if (dto.phoneNumber() != null) {
            this.phoneNumber = dto.phoneNumber();
        }
        if (dto.password() != null) {
            this.password = dto.password();
        }
        if (dto.role() != null) {
            this.role = dto.role();
        }
    }
}
