package com.microservice.patrols.controller;

import com.microservice.patrols.dto.CreatePatrolDTO;
import com.microservice.patrols.dto.PatrolResponseDTO;
import com.microservice.patrols.dto.PatrolSummaryDTO;
import com.microservice.patrols.dto.UpdatePatrolDTO;
import com.microservice.patrols.service.PatrolService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/patrols")
public class PatrolController {
    private final PatrolService patrolService;
    @Autowired
    public PatrolController(PatrolService patrolService) {
        this.patrolService = patrolService;
    }

    // PatrolSummaryDTO y PatrolResponseDTO son iguales, entonces quedarnos solo con uno?
    @PostMapping
    public ResponseEntity<PatrolResponseDTO> createPatrol(@RequestBody @Valid CreatePatrolDTO createPatrolDTO,
                                                          UriComponentsBuilder uriComponentsBuilder) {
        PatrolResponseDTO patrolResponseDTO = patrolService.create(createPatrolDTO);
        URI url = uriComponentsBuilder.path("/api/patrols/{id}").buildAndExpand(patrolResponseDTO.id()).toUri();
        return ResponseEntity.created(url).body(patrolResponseDTO); // 201
    }

    @GetMapping
    public ResponseEntity<Page<PatrolSummaryDTO>> getAllPatrols(@PageableDefault(size = 10, sort = "id", page=0)
                                                                Pageable pageable) {
        return ResponseEntity.ok(patrolService.findAll(pageable)); // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatrolSummaryDTO> getPatrolById(@PathVariable Long id) {
        return ResponseEntity.ok(patrolService.findById(id)); // 200
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PatrolResponseDTO> updatePatrol(@PathVariable Long id,
                                                          @RequestBody @Valid UpdatePatrolDTO updatePatrolDTO) {
        return ResponseEntity.ok(patrolService.update(id, updatePatrolDTO)); // 200
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deletePatrol(@PathVariable Long id) {
        patrolService.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
