package com.microservice.incidents.service;

import com.microservice.incidents.client.PatrolClient;
import com.microservice.incidents.client.UserClient;
import com.microservice.incidents.dto.CreateIncidentAssignmentDTO;
import com.microservice.incidents.dto.IncidentAssignmentResponseDTO;
import com.microservice.incidents.dto.PatrolResponseDTO;
import com.microservice.incidents.dto.UserResponseDTO;
import com.microservice.incidents.entities.Incident;
import com.microservice.incidents.entities.IncidentAssignment;
import com.microservice.incidents.persistence.IncidentAssigmentRepository;
import com.microservice.incidents.persistence.IncidentRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IncidentAssignmentService {
    private final IncidentAssigmentRepository incidentAssigmentRepository;
    private final IncidentRepository incidentRepository;
    private final UserClient userClient;
    private final PatrolClient patrolClient;

    @Autowired
    public IncidentAssignmentService(IncidentAssigmentRepository incidentAssigmentRepository,
                                     IncidentRepository incidentRepository, UserClient userClient,
                                     PatrolClient patrolClient) {
        this.incidentAssigmentRepository = incidentAssigmentRepository;
        this.incidentRepository = incidentRepository;
        this.userClient = userClient;
        this.patrolClient = patrolClient;
    }

    public IncidentAssignmentResponseDTO create(CreateIncidentAssignmentDTO datos) {
        // 1. validar que el incidente exista
        Incident incident = incidentRepository.findById(datos.incidentId()).orElseThrow(() ->
                new EntityNotFoundException("Incidente no encontrado con ID: " + datos.incidentId()));

        // 2. validar que el usuario remoto exista
        try {
            UserResponseDTO user = userClient.getUserById(datos.userId());

            // validar que el usuario sea POLICE
            if (!user.role().equals("POLICE")) {

                System.out.println("ROL: " + user.role());
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "El usuario no tiene el rol de POLICE para realizar esta operación.");
            }

            // capturamos las excepciones del global handler de usuario
        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + datos.userId());
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al consultar el servicio de usuarios");
        }


        // verificar si el policía tiene patrulla y en caso tenga asignarle un patrolId
        // patrolClient.getPatrolAssignmentsByUserId(datos.userId());
        // al hacer esto eliminar el paso 3. ya que la patrulla sí existe cuando se hace esta validación

        // 3. en caso el policía tenga patrulla entonces validar que la patrulla exista ¿? no es necesario esto
        if (datos.patrolId() != null) {
            try {
                PatrolResponseDTO patrol = patrolClient.getPatrolById(datos.patrolId());
                // capturamos las excepciones del global handler de patrulla
            } catch (FeignException.NotFound ex) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patrulla no encontrada con ID: " + datos.patrolId());
            } catch (FeignException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al consultar el servicio de patrullas");
            }
        }

        // 4. crear la asignación
        IncidentAssignment incidentAssignment = new IncidentAssignment(datos);
        incidentAssignment.setIncident(incident); // seteamos la referencia del objeto para evitar errores

        // 5. guardar y devolver
        return new IncidentAssignmentResponseDTO(incidentAssigmentRepository.save(incidentAssignment));
    }
}
