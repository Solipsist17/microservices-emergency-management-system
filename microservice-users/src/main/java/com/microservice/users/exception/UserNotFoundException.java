package com.microservice.users.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("Usuario no encontrado con ID: " + id);
    }
}
