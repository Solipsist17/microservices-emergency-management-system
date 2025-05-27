package com.microservice.users.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class BadRequestException extends RuntimeException {
    private final String field;
    public BadRequestException(String field, String message) {
        super(message);
        this.field = field;
    }
}
