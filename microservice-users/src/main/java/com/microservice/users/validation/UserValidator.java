package com.microservice.users.validation;

import com.microservice.users.dto.CreateUserDTO;

public interface UserValidator {
    void validate(CreateUserDTO data);
}
