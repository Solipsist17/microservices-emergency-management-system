package com.microservice.incidents.validation;

import com.microservice.incidents.client.UserClient;
import com.microservice.incidents.dto.CreateIncidentDTO;
import com.microservice.incidents.dto.UpdateIncidentDTO;
import com.microservice.incidents.dto.UserResponseDTO;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserExistenceValidator implements IncidentValidator {
    private final UserClient userClient;
    @Autowired // autowired no es necesario si solo tiene un constructor
    public UserExistenceValidator(UserClient userClient) {
        this.userClient = userClient;
    }
    @Override
    public void validate(CreateIncidentDTO datos) {
        try {
            UserResponseDTO user = userClient.getUserById(datos.reportedBy());
        } catch (FeignException.NotFound ex) {
            // feign captura la excepción del global handler de usuario solo sabiendo que fue un 404
            // entonces ahora el microservicio incidents genera su propio 404:
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + datos.reportedBy());
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al consultar el servicio de usuarios");
        }
    }
}
