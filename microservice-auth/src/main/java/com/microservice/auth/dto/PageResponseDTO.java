package com.microservice.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PageResponseDTO(
        @JsonProperty("content") List<UserDataDTO> content,
        @JsonProperty("totalElements") long totalElements,
        @JsonProperty("empty") boolean empty
        ) {
}

record UserDataDTO(
    @JsonProperty("id") Long id,
    @JsonProperty("email") String email,
    @JsonProperty("password") String password,  // El JSON tiene "password", no "passwordHash"
    @JsonProperty("role") String role,
    @JsonProperty("name") String name,
    @JsonProperty("lastName") String lastName,
    @JsonProperty("dni") String dni,
    @JsonProperty("phoneNumber") String phoneNumber
){

}
