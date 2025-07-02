package com.microservice.patrols.persistence;

import com.microservice.patrols.entities.PatrolAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatrolAssignmentRepository extends JpaRepository<PatrolAssignment, Long> {
}
