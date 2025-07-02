package com.microservice.incidents.client;

import com.microservice.incidents.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-users")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long userId);

    /*@GetMapping("/api/users/patrol-assignments/{id}")
    PatrolAssignmentResponseDTO getPatrolAssignmentsByUserId(@PathVariable("id") Long userId);*/
}
