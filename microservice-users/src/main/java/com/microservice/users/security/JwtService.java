package com.microservice.users.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private static final String SECRET = "${jwt.secret}";
    private static final String ISSUER = "microservice-auth";

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    private final JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build();

    public DecodedJWT validateToken(String token) {
        return verifier.verify(token);
    }

    public String extractUserId(DecodedJWT jwt) {
        return jwt.getSubject();
    }

    public String extractRole(DecodedJWT jwt) {
        return jwt.getClaim("role").asString();
    }
}
