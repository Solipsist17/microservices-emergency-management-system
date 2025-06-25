package com.microservice.incidents.service;

import com.microservice.incidents.dto.CreateIncidentDTO;
import com.microservice.incidents.dto.IncidentResponseDTO;
import com.microservice.incidents.dto.IncidentSummaryDTO;
import com.microservice.incidents.dto.UpdateIncidentDTO;
import com.microservice.incidents.entities.Incident;
import com.microservice.incidents.persistence.IncidentRepository;
import com.microservice.incidents.validation.IncidentValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentService {
    private final IncidentRepository incidentRepository;
    private List<IncidentValidator> validators;

    @Autowired
    public IncidentService(IncidentRepository incidentRepository, List<IncidentValidator> validators){
        this.incidentRepository = incidentRepository;
        this.validators = validators;
    }

    public IncidentResponseDTO create(CreateIncidentDTO datos){
        validators.forEach(v -> v.validate(datos));
        Incident incident=new Incident(datos);
        return new IncidentResponseDTO(incidentRepository.save(incident));
    }

    public Page<IncidentSummaryDTO> findAll(Pageable pageable) {
        return incidentRepository.findAll(pageable).map(incident -> new IncidentSummaryDTO(incident));
    }

    public IncidentResponseDTO update(Long id, UpdateIncidentDTO datos) {
        if(!incidentRepository.existsById(id)) {
            throw new EntityNotFoundException("Este id para el incidente no fue encontrado");
        }
        Incident incident = incidentRepository.getReferenceById(id);
        incident.update(datos);
        return new IncidentResponseDTO(incident);
    }

    public void delete(Long id) {
        if(!incidentRepository.existsById(id)){
            throw new EntityNotFoundException(("Este id para el incidente no fue encontrado"));
        }

        Incident incident = incidentRepository.getReferenceById(id);
        incidentRepository.delete(incident);
    }
}
