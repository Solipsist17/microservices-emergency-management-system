package com.microservice.patrols.controller;

import com.microservice.patrols.dto.CreatePatrolAssignmentDTO;
import com.microservice.patrols.dto.PatrolAssignmentResponseDTO;
import com.microservice.patrols.dto.PatrolAssignmentSummaryDTO;
import com.microservice.patrols.dto.UpdatePatrolAssignmentDTO;
import com.microservice.patrols.service.PatrolAssignmentService;
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
@RequestMapping("/api/patrol-assignments")
public class PatrolAssignmentController {
    private final PatrolAssignmentService patrolAssignmentService;
    @Autowired
    public PatrolAssignmentController(PatrolAssignmentService patrolAssignmentService) {
        this.patrolAssignmentService = patrolAssignmentService;
    }

    @PostMapping
    public ResponseEntity<PatrolAssignmentResponseDTO> createPatrolAssignment(
            @RequestBody @Valid CreatePatrolAssignmentDTO requestDTO,
            UriComponentsBuilder uriComponentsBuilder) {
        PatrolAssignmentResponseDTO responseDTO = patrolAssignmentService.create(requestDTO);
        URI url = uriComponentsBuilder.path("/api/patrol-assignments/{id}").buildAndExpand(responseDTO.id()).toUri();
        return ResponseEntity.created(url).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PatrolAssignmentSummaryDTO>> getAllPatrolAssignments(
            @PageableDefault(size = 10, sort = "id", page=0) Pageable pageable) {
        return ResponseEntity.ok(patrolAssignmentService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatrolAssignmentSummaryDTO> getPatrolAssignmentById(@PathVariable Long id) {
        return ResponseEntity.ok(patrolAssignmentService.findById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PatrolAssignmentResponseDTO> updatePatrolAssignment(
            @PathVariable Long id, @RequestBody @Valid UpdatePatrolAssignmentDTO updatePatrolAssignmentDTO) {
        return ResponseEntity.ok(patrolAssignmentService.update(id, updatePatrolAssignmentDTO));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deletePatrolAssignment(@PathVariable Long id) {
        patrolAssignmentService.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
