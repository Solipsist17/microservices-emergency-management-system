package com.microservice.auth.client;

import com.microservice.auth.dto.CreateUserRequestDTO;
import com.microservice.auth.dto.UserInfoDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="microservice-user", url="http://localhost:8081")
public interface UserClient {
    @GetMapping("/api/users/by-email")
    UserInfoDTO getByEmail(@RequestParam("email") String email);

    @PostMapping("/api/users")
    UserInfoDTO createUser(@RequestBody @Valid CreateUserRequestDTO userRequestDTO);
}
