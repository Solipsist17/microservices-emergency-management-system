package com.microservice.incidents.persistence;

import com.microservice.incidents.entities.IncidentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentAssigmentRepository extends JpaRepository<IncidentAssignment, Long> {
}
