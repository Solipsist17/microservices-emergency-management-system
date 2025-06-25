package com.microservice.incidents.controller;

import com.microservice.incidents.dto.CreateIncidentDTO;
import com.microservice.incidents.dto.IncidentResponseDTO;
import com.microservice.incidents.dto.IncidentSummaryDTO;
import com.microservice.incidents.dto.UpdateIncidentDTO;
import com.microservice.incidents.service.IncidentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentService incidentService;
    @Autowired
    public IncidentController(IncidentService incidentService){ this.incidentService = incidentService; }

    @PostMapping
    public ResponseEntity<IncidentResponseDTO> createIncident(@RequestBody CreateIncidentDTO createIncidentDTO) {
        IncidentResponseDTO incidentResponseDTO = incidentService.create(createIncidentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(incidentResponseDTO);
    }
    @GetMapping
    public ResponseEntity<Page<IncidentSummaryDTO>> getAllIncidents(@PageableDefault(size = 10, sort = "createdAt", page=0)
                                                                     Pageable pageable){
        return ResponseEntity.ok(incidentService.findAll(pageable));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<IncidentResponseDTO> updateIncident(@PathVariable Long id,
                                                              @RequestBody UpdateIncidentDTO updateIncidentDTO){
        return ResponseEntity.ok(incidentService.update(id, updateIncidentDTO));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deleteIncident(@PathVariable Long id) {
        incidentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
