package com.microservice.patrols.client;

import com.microservice.patrols.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-users")
public interface UserClient {
    @GetMapping("/api/users/{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long userId);
}
