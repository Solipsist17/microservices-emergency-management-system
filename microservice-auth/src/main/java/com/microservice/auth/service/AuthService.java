package com.microservice.auth.service;


import com.microservice.auth.client.UserClient;
import com.microservice.auth.dto.LoginRequestDTO;
import com.microservice.auth.dto.LoginResponseDTO;
import com.microservice.auth.dto.UserInfoDTO;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserClient userClient;
    private final JwtService jwtService;


    public AuthService(UserClient userClient, JwtService jwtService){
        this.userClient = userClient;
        this.jwtService = jwtService;
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
            // Debug logging
            //System.out.println("=== DEBUG AUTH ===");
            //System.out.println("Email buscado: " + request.email());
//            System.out.println("Usuario encontrado: " + (user != null));
//            if (user != null) {
//                System.out.println("User ID: " + user.id());
//                System.out.println("User Role: " + user.role());
//                System.out.println("Password presente: " + (user.password() != null));
//                System.out.println("Password vacío: " + (user.password() != null && user.password().trim().isEmpty()));
//                if (user.password() != null) {
//                    System.out.println("Password longitud: " + user.password().length());
//                    System.out.println("Password primeros chars: " + user.password().substring(0, Math.min(10, user.password().length())));
//                }
//            }
//            System.out.println("==================");

            // Validar que el usuario existe
            if (user == null) {
                throw new BadCredentialsException("Usuario no encontrado");
            }

            // Validar que el hash de la contraseña no sea null
            //if (user.password() == null || user.password().trim().isEmpty()) {
            //    throw new BadCredentialsException("Error en datos de usuario");
            //}

//            if (!BCrypt.checkpw(request.password(), user.password())) {
//                throw new BadCredentialsException("Contraseña inválida");
//            }

            //if (!request.password().equals(user.password())) {
            //    throw new BadCredentialsException("Contraseña inválida");
            //}

            String token = jwtService.generateToken(user.id(), user.role());
            return new LoginResponseDTO(token);
        } catch (FeignException.NotFound ex) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credenciales incorrectas: " + request.email());
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al consultar el servicio de user");
        }
        /*}catch (Exception e) {
            System.out.println("Error en autenticación: "+e.getMessage());

            // Si es BadCredentialsException, re-lanzar
            if (e instanceof BadCredentialsException) {
                throw e;
            }

            // Para otros errores, lanzar BadCredentialsException genérica
            throw new BadCredentialsException("Error en autenticación");
        }*/
    }
}
