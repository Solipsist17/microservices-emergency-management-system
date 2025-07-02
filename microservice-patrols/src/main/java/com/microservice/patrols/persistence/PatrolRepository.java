package com.microservice.patrols.persistence;

import com.microservice.patrols.entities.Patrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatrolRepository extends JpaRepository<Patrol, Long> {
}
