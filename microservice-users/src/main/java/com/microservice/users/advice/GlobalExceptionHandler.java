package com.microservice.users.advice;

import com.microservice.users.exception.BadRequestException;
import com.microservice.users.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // manejador global de errores para los RestController
public class GlobalExceptionHandler {

    // maneja las excepciones lanzadas cuando fallan las validaciones @Valid en un @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Captura los errores personalizados como BadRequestException
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(BadRequestException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ex.getField(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // lanzar 401 unauthorized
    // Maneja error de usuario no existente respondiendo con un 404 "error: usuario no encontrado con ID: "
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFound(UserNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        System.out.println("Lanzando handleUserNotFound()");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        System.out.println("Lanzando handleEntityNotFoundException()");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Excepción de valores duplicados (dni)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicateEntry(DataIntegrityViolationException ex) {
        Throwable rootCause = getRootCause(ex);

        if (rootCause instanceof SQLException sqlEx &&
                sqlEx.getMessage().contains("Duplicate entry")) {

            String message = sqlEx.getMessage();
            String value = extract(message, "Duplicate entry '", "'");
            String field = extract(message, "for key '", "'");

            Map<String, String> response = new HashMap<>();
            response.put("error", "Ya existe un usuario con ese dni: " + value);

            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Violación de integridad de datos."));
    }

    // Busca la causa raíz
    private Throwable getRootCause(Throwable ex) {
        Throwable cause = ex;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }

    private String extract(String text, String startToken, String endToken) {
        try {
            int start = text.indexOf(startToken) + startToken.length();
            int end = text.indexOf(endToken, start);
            return text.substring(start, end);
        } catch (Exception e) {
            return "desconocido";
        }
    }

}