package com.microservice.incidents.controller;

import com.microservice.incidents.dto.CreateIncidentAssignmentDTO;
import com.microservice.incidents.dto.IncidentAssignmentResponseDTO;
import com.microservice.incidents.service.IncidentAssignmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/incident-assignments")
public class IncidentAssignmentController {
    private final IncidentAssignmentService incidentAssignmentService;
    @Autowired
    public IncidentAssignmentController(IncidentAssignmentService incidentAssignmentService) {
        this.incidentAssignmentService = incidentAssignmentService;
    }

    @PostMapping
    public ResponseEntity<IncidentAssignmentResponseDTO> createIncidentAssignment(
            @RequestBody @Valid CreateIncidentAssignmentDTO requestDTO, UriComponentsBuilder uriComponentsBuilder) {
        IncidentAssignmentResponseDTO responseDTO = incidentAssignmentService.create(requestDTO);
        URI url = uriComponentsBuilder.path("/api/incident-assignments/{id}").buildAndExpand(responseDTO.id()).toUri();
        return ResponseEntity.created(url).body(responseDTO);
    }
}
