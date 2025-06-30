package com.microservice.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;



public record UserInfoDTO(
        Long id,
        String email,
        String password,
        String role
) {

}