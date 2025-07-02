package com.microservice.patrols.service;

import com.microservice.patrols.client.UserClient;
import com.microservice.patrols.dto.*;
import com.microservice.patrols.entities.Patrol;
import com.microservice.patrols.entities.PatrolAssignment;
import com.microservice.patrols.persistence.PatrolAssignmentRepository;
import com.microservice.patrols.persistence.PatrolRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PatrolAssignmentService {
    private final PatrolAssignmentRepository patrolAssignmentRepository;
    private final PatrolRepository patrolRepository;
    private final UserClient userClient;
    @Autowired
    public PatrolAssignmentService(PatrolAssignmentRepository patrolAssignmentRepository,
                                   PatrolRepository patrolRepository, UserClient userClient) {
        this.patrolAssignmentRepository = patrolAssignmentRepository;
        this.patrolRepository = patrolRepository;
        this.userClient = userClient;
    }

    public PatrolAssignmentResponseDTO create(CreatePatrolAssignmentDTO datos) {
        // 1. validar que la patrulla exista
        Patrol patrol = patrolRepository.findById(datos.patrolId()).orElseThrow(() ->
                new EntityNotFoundException("Patrulla no encontrada con ID: " + datos.patrolId()));

        // 2. validar que el usuario remoto exista
        try {
            UserResponseDTO user = userClient.getUserById(datos.userId());
            // capturamos las excepciones del global handler de usuario
        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + datos.userId());
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al consultar el servicio de usuarios");
        }

        // 3. crear la asignación
        PatrolAssignment patrolAssignment = new PatrolAssignment(datos);
        patrolAssignment.setPatrol(patrol); // seteamos la referencia del objeto para evitar errores

        // 4. guardar y devolver
        return new PatrolAssignmentResponseDTO(patrolAssignmentRepository.save(patrolAssignment));
    }

    public Page<PatrolAssignmentSummaryDTO> findAll(Pageable pageable) {
        return patrolAssignmentRepository.findAll(pageable)
                .map(patrolAssignment -> new PatrolAssignmentSummaryDTO(patrolAssignment));
    }

    public PatrolAssignmentSummaryDTO findById(Long id) {
        PatrolAssignment patrolAssignment = patrolAssignmentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Asignación de patrulla no encontrada con ID: " + id));
        return new PatrolAssignmentSummaryDTO(patrolAssignment);
    }

    public PatrolAssignmentResponseDTO update(Long id, UpdatePatrolAssignmentDTO datos) {
        // validators de lógica de negocio aquí

        // 1. validar que patrolassignment exista
        if (!patrolAssignmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Asignación de patrulla no encontrada con ID: " + id);
        }

        // 2. validar que la patrulla exista
        Patrol patrol = patrolRepository.findById(datos.patrolId()).orElseThrow(() ->
                new EntityNotFoundException("Patrulla no encontrada con ID: " + datos.patrolId()));

        // 3. validar que el usuario remoto exista
        try {
            UserResponseDTO user = userClient.getUserById(datos.userId());
            // capturamos las excepciones del global handler de usuario
        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + datos.userId());
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al consultar el servicio de usuarios");
        }

        // 4. actualizar la asignación
        PatrolAssignment patrolAssignment = patrolAssignmentRepository.getReferenceById(id);
        patrolAssignment.update(datos);
        patrolAssignment.setPatrol(patrol); // seteamos la referencia del objeto para evitar errores
        return new PatrolAssignmentResponseDTO(patrolAssignment);
    }

    public void delete(Long id) {
        // validators de lógica de negocio aquí
        if (!patrolAssignmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Asignación de patrulla no encontrada con ID: " + id);
        }
        PatrolAssignment patrolAssignment = patrolAssignmentRepository.getReferenceById(id);
        patrolAssignmentRepository.delete(patrolAssignment);
    }
}
