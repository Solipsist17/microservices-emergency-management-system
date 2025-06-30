package com.microservice.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.microservice.auth.dto.UserInfoDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}") // en milisegundos, ej. 3600000 para 1h
    private long expirationTime;

    @PostConstruct
    public void init() {
        System.out.println("JWT Secret: " + secret);
        System.out.println("JWT Secret is null: " + (secret == null));
        System.out.println("JWT Expiration: " + expirationTime);
    }

    private Algorithm getAlgorithm() {
        System.out.println("=== GET ALGORITHM ===");
        System.out.println("Secret value: '" + secret + "'");
        System.out.println("Secret is null: " + (secret == null));
        System.out.println("Secret is empty: " + (secret != null && secret.isEmpty()));
        System.out.println("Secret length: " + (secret != null ? secret.length() : "null"));
        if (secret == null) {
            System.out.println("ERROR: Secret is null!");
            throw new IllegalArgumentException("JWT secret is null");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            System.out.println("Algorithm created successfully");
            return algorithm;
        } catch (Exception e) {
            System.out.println("Error creating algorithm: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }
    public String generateToken(Long userId, String role) {

        System.out.println("=== GENERATE TOKEN START ===");
        System.out.println("UserId: " + userId);
        System.out.println("Role: " + role);
        System.out.println("Secret in generateToken: '" + secret + "'");
        System.out.println("ExpirationTime: " + expirationTime);

        try {
            Algorithm alg = getAlgorithm();
            return JWT.create()
                    .withIssuer("microservice-auth")
                    .withSubject(userId.toString())
                    .withClaim("role", role)
                    .withIssuedAt(new Date())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(alg);
        } catch(Exception e){
            System.out.println("ERROR in generateToken: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    public DecodedJWT validateToken(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        return verifier.verify(token);
    }

    public String extractUserId(String token) {
        return validateToken(token).getSubject();
    }

    public String extractUserRole(String token) {
        return validateToken(token).getClaim("role").asString();
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}

