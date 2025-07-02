package com.microservice.incidents.client;

import com.microservice.incidents.dto.PatrolResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-patrols")
public interface PatrolClient {
    @GetMapping("/api/patrols/{id}")
    PatrolResponseDTO getPatrolById(@PathVariable("id") Long patrolId);
}
