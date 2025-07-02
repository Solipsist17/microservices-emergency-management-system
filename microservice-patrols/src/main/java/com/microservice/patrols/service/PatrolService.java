package com.microservice.patrols.service;

import com.microservice.patrols.dto.CreatePatrolDTO;
import com.microservice.patrols.dto.PatrolResponseDTO;
import com.microservice.patrols.dto.PatrolSummaryDTO;
import com.microservice.patrols.dto.UpdatePatrolDTO;
import com.microservice.patrols.entities.Patrol;
import com.microservice.patrols.persistence.PatrolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatrolService {
    private final PatrolRepository patrolRepository;
    //private List<PatrolValidator> validators;

    @Autowired
    public PatrolService(PatrolRepository patrolRepository) {
        this.patrolRepository = patrolRepository;
    }

    public PatrolResponseDTO create(CreatePatrolDTO datos) {
        // validators aquí
        Patrol patrol = new Patrol(datos);
        return new PatrolResponseDTO(patrolRepository.save(patrol));
    }

    public Page<PatrolSummaryDTO> findAll(Pageable pageable) {
        return patrolRepository.findAll(pageable).map(patrol -> new PatrolSummaryDTO(patrol));
    }

    public PatrolSummaryDTO findById(Long id) {
        Patrol patrol = patrolRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Patrulla no encontrada con ID: " + id));
        return new PatrolSummaryDTO(patrol);
    }

    public PatrolResponseDTO update(Long id, UpdatePatrolDTO datos) {
        // validators aquí
        if (!patrolRepository.existsById(id)) {
            throw new EntityNotFoundException("Patrulla no encontrada con ID: " + id);
        }
        Patrol patrol = patrolRepository.getReferenceById(id);
        patrol.update(datos);
        return new PatrolResponseDTO(patrol);
    }

    public void delete(Long id) {
        // validators aquí
        if (!patrolRepository.existsById(id)) {
            throw new EntityNotFoundException("Patrulla no encontrada con ID: " + id);
        }
        Patrol patrol = patrolRepository.getReferenceById(id);
        patrolRepository.delete(patrol);
    }
}
