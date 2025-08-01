package com.microservice.incidents.controller;

import com.microservice.incidents.dto.CreateIncidentDTO;
import com.microservice.incidents.dto.IncidentResponseDTO;
import com.microservice.incidents.dto.IncidentSummaryDTO;
import com.microservice.incidents.dto.UpdateIncidentDTO;
import com.microservice.incidents.service.IncidentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentService incidentService;
    @Autowired
    public IncidentController(IncidentService incidentService){ this.incidentService = incidentService; }

    @PostMapping
    public ResponseEntity<IncidentResponseDTO> createIncident(@RequestBody CreateIncidentDTO createIncidentDTO,
                                                              @RequestHeader("X-User-Id") String userId, // viene desde el gateway
                                                              UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("Desde msvc Incident createIncident, userId: " + userId);

        IncidentResponseDTO incidentResponseDTO = incidentService.create(createIncidentDTO, userId);
        URI url = uriComponentsBuilder.path("/api/incidents/{id}").buildAndExpand(incidentResponseDTO.id()).toUri();
        //return ResponseEntity.status(HttpStatus.CREATED).body(incidentResponseDTO);
        return ResponseEntity.created(url).body(incidentResponseDTO);
    }
    @GetMapping
    public ResponseEntity<Page<IncidentSummaryDTO>> getAllIncidents(@PageableDefault(size = 10, sort = "createdAt", page=0)
                                                                     Pageable pageable){
        return ResponseEntity.ok(incidentService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentSummaryDTO> getIncidentById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.findById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<IncidentResponseDTO> updateIncident(@PathVariable Long id,
                                                              @RequestBody @Valid UpdateIncidentDTO updateIncidentDTO){
        return ResponseEntity.ok(incidentService.update(id, updateIncidentDTO));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deleteIncident(@PathVariable Long id) {
        incidentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
