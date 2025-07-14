package com.microservice.incidents.service;

import com.microservice.incidents.client.UserClient;
import com.microservice.incidents.dto.*;
import com.microservice.incidents.entities.Incident;
import com.microservice.incidents.persistence.IncidentRepository;
import com.microservice.incidents.validation.IncidentValidator;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IncidentService {
    private final IncidentRepository incidentRepository;
    private final UserClient userClient;
    private List<IncidentValidator> validators;

    @Autowired
    public IncidentService(IncidentRepository incidentRepository, UserClient userClient,
                           List<IncidentValidator> validators){
        this.incidentRepository = incidentRepository;
        this.userClient = userClient;
        this.validators = validators;
    }

    public IncidentResponseDTO create(CreateIncidentDTO datos, String userId){
        //validators.forEach(v -> v.validate(datos));

        // validar existencia de usuario
        try {
            UserResponseDTO user = userClient.getUserById(Long.parseLong(userId));

            // validar que el usuario sea CITIZEN
            if (!user.role().equals("CITIZEN")) {

                System.out.println("ROL: " + user.role());
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "El usuario no tiene el rol de CITIZEN para realizar esta operación.");
            }

            // capturamos las excepciones del global handler de usuario
        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + userId);
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al consultar el servicio de usuarios");
        }

        Incident incident = new Incident(datos);
        incident.setReportedBy(Long.parseLong(userId));
        return new IncidentResponseDTO(incidentRepository.save(incident));
    }

    public Page<IncidentSummaryDTO> findAll(Pageable pageable) {
        return incidentRepository.findAll(pageable).map(incident -> new IncidentSummaryDTO(incident));
    }

    public IncidentSummaryDTO findById(Long id) {
        Incident incident = incidentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Incidente no encontrado con ID: " + id));
        return new IncidentSummaryDTO(incident);
    }

    public IncidentResponseDTO update(Long id, UpdateIncidentDTO datos) {
        if(!incidentRepository.existsById(id)) {
            throw new EntityNotFoundException("Incidente no encontrado con ID: " + id);
        }
        // no se permite actualizar el id del usuario que reportó el incidente
        Incident incident = incidentRepository.getReferenceById(id);
        incident.update(datos);
        return new IncidentResponseDTO(incident);
    }

    public void delete(Long id) {
        if(!incidentRepository.existsById(id)){
            throw new EntityNotFoundException(("Incidente no encontrado con ID: " + id));
        }

        Incident incident = incidentRepository.getReferenceById(id);
        incidentRepository.delete(incident);
    }
}
