package com.microservice.auth.controller;

import com.microservice.auth.client.UserClient;
import com.microservice.auth.dto.LoginRequestDTO;
import com.microservice.auth.dto.LoginResponseDTO;
import com.microservice.auth.dto.UserInfoDTO;
import com.microservice.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request){

        LoginResponseDTO response = authService.login(request);
        System.out.println(response);
        return ResponseEntity.ok(response);

    }
}
