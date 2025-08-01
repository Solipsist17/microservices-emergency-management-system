package com.microservice.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;



public record UserInfoDTO(
        Long id,
        String dni,
        String email,
        String phoneNumber,
        String password,
        String role
) {

}