package com.microservice.auth.service;


import com.microservice.auth.client.UserClient;
import com.microservice.auth.dto.*;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserClient userClient;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserClient userClient, JwtService jwtService, PasswordEncoder passwordEncoder){
        this.userClient = userClient;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        // Validar que los datos de entrada no sean null
        if (request.email() == null || request.email().trim().isEmpty()) {
            throw new BadCredentialsException("Email es requerido");
        }

        if (request.password() == null || request.password().trim().isEmpty()) {
            throw new BadCredentialsException("Contraseña es requerida");
        }

        try {
            UserInfoDTO user = userClient.getByEmail(request.email());

            // validar que no se pueda ingresar con credenciales incorrectas
            if (!passwordEncoder.matches(request.password(), user.password())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Credenciales incorrectas: " + request.email());
            }

            // generamos el token y enviamos
            String token = jwtService.generateToken(user.id(), user.role());
            return new LoginResponseDTO(token);

        } catch (FeignException.NotFound ex) {
            System.out.println("Lanzando: FeignException.NotFound");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Credenciales incorrectas: " + request.email());
        } catch (FeignException ex) {
            System.out.printf("Lanzando: FeignException");
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al consultar el servicio de user");
        }

    }

    // solo registra usuarios CITIZEN, los de rol POLICE deben ser registrados dentro del sistema o panel administrativo
    public RegisterResponseDTO register(RegisterResquestDTO request) {
        // hasheamos
        String hashedPassword = passwordEncoder.encode(request.password());

        CreateUserRequestDTO createUserRequest = new CreateUserRequestDTO(
                request.dni(),
                request.email(),
                request.phoneNumber(),
                hashedPassword,
                request.role() // asumimos que el role es CITIZEN
        );
        try {
            // Llamamos al user-service para registrar
            UserInfoDTO createdUser = userClient.createUser(createUserRequest);

            // generar el token automáticamente si queremos logear al usuario al registrarse
            //String token = jwtService.generateToken(createdUser.id(), createdUser.role());

            return new RegisterResponseDTO(
                    createdUser.id(),
                    createdUser.dni(),
                    createdUser.email(),
                    createdUser.phoneNumber(),
                    createdUser.password(),
                    createdUser.role());

        } catch (FeignException.Conflict ex) { // crear excepción en rest controller advice de user
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El dni ya está registrado: " + createUserRequest.dni());
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al conectar con el servicio de usuario");
        }
    }
}
