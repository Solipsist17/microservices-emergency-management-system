package com.microservice.users.service;

import com.microservice.users.dto.CreateUserDTO;
import com.microservice.users.dto.UpdateUserDTO;
import com.microservice.users.dto.UserResponseDTO;
import com.microservice.users.dto.UserSummaryDTO;
import com.microservice.users.entities.Role;
import com.microservice.users.entities.User;
import com.microservice.users.persistence.UserRepository;
import com.microservice.users.validation.UserValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private List<UserValidator> validators;

    @Autowired
    public UserService(UserRepository userRepository, List<UserValidator> validators) {
        this.userRepository = userRepository;
        this.validators = validators;
    }

    public UserResponseDTO create(CreateUserDTO datos) {
        validators.forEach(v -> v.validate(datos));

        User user = new User(datos);
        return new UserResponseDTO(userRepository.save(user));
    }

    public Page<UserSummaryDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(user -> new UserSummaryDTO(user));
    }

    public UserResponseDTO update(Long id, UpdateUserDTO datos) {
        // validación existencia del usuario por id
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Este id para el usuario no fue encontrado");
        }
        User user = userRepository.getReferenceById(id);
        user.update(datos);
        return new UserResponseDTO(user);
    }

    public void delete(Long id) {
        // validación existencia del usuario por id
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Este id para el usuario no fue encontrado");
        }

        User user = userRepository.getReferenceById(id);
        userRepository.delete(user);
    }
}
