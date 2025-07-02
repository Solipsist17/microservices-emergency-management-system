package com.microservice.users.controller;

import com.microservice.users.dto.*;
import com.microservice.users.entities.User;
import com.microservice.users.service.IUserService;
import com.microservice.users.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        UserResponseDTO userResponseDTO = userService.create(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO); // 201 status code
    }

    @GetMapping
    public ResponseEntity<Page<UserSummaryDTO>> getAllUsers(@PageableDefault(size = 10, sort = "createdAt", page=0)
                                                                Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable)); // 200 status code
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    @GetMapping("/by-email")
    public ResponseEntity<UserSummaryDTO> getByEmail(@RequestParam String email){
        UserSummaryDTO user = userService.findByEmailSingle(email);
        System.out.println(user);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,
                                                      @RequestBody @Valid UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.update(id, updateUserDTO)); // 200 status code
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build(); // 204 status code
    }
}
