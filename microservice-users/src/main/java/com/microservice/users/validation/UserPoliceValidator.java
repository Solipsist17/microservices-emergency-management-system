package com.microservice.users.validation;

import com.microservice.users.dto.CreateUserDTO;
import com.microservice.users.entities.Role;
import com.microservice.users.exception.BadRequestException;
import org.springframework.stereotype.Component;

// validación campos no nulos en usuario policía
@Component
public class UserPoliceValidator implements UserValidator{
    @Override
    public void validate(CreateUserDTO datos) {
        if (datos.role() == Role.POLICE) {
            if (datos.name() == null || datos.name().isBlank()) {
                throw new BadRequestException("name", "El nombre es obligatorio para policías");
            }
            if (datos.lastName() == null || datos.lastName().isBlank()) {
                throw new BadRequestException("lastName", "El apellido es obligatorio para policías");
            }
        }
    }
}
