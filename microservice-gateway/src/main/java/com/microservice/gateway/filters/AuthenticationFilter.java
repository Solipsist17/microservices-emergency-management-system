package com.microservice.gateway.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Order(1) // define prioridad entre múltiples filtros globales
public class AuthenticationFilter implements GlobalFilter {
    @Value("${jwt.secret}")
    private String secret;

    // Rutas públicas que no requieren validación del JWT
    private static final List<String> openEndpoints = List.of(
            "/api/auth/login",
            "/api/auth/register"/*,
            "/api/users"*/
    );

    // Verificar el JWT en cada request que pasa por este msvc de api gateway
    // y si es válido añadir el id del usuario en los headers de la request para que los msvc lo usen

    // modelo reactivo Spring WebFlux: ideal para alta concurrencia y servcios tipo gateway
    // ServerWebExchange: similar a HttpServletRequest/Response pero asíncrono y no bloqueante
    // Mono<T> y Flux<T>: representan valores asíncronos (como promesas en JS)
    // exchange: el contexto de la solicitud y respuesta http en un entorno reactivo
    // chain: representa la cadena de filtros del gateway
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        System.out.println("🛡️ PATH recibido: " + path);

        // ✅ Si es ruta pública, no validar JWT
        if (openEndpoints.stream().anyMatch(path::startsWith)) {
            System.out.println("🔓 Ruta pública, no se valida JWT");
            return chain.filter(exchange);
        }

        // ⚠️ Si no contiene el header Authorization → 401
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return this.onError(exchange, "Falta el header Authorization", HttpStatus.UNAUTHORIZED);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return this.onError(exchange, "Authorization inválido", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        System.out.println("🔎 Authorization header: " + authHeader);
        System.out.println("🧪 token quitando Bearer: " + token);

        try {
            // verificar el token con el secret definido
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            // extrae el subject
            String userId = decodedJWT.getSubject();
            System.out.println("✅ JWT verificado. userId extraído: " + userId);

            // agregamos el userId como header
            // para que los msvc lean directamente el id sin procesar el jwt de nuevo
            request = request.mutate()
                    .header("X-User-Id", userId)
                    .build();
            exchange = exchange.mutate().request(request).build();

        } catch (JWTVerificationException e) {
            return onError(exchange, "JWT inválido: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        return chain.filter(exchange); // pasamos el control al siguiente filtro en la cadena
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        System.out.println("❌ ERROR: " + message);
        exchange.getResponse().setStatusCode(status); // modificamos para devolver el status code
        return exchange.getResponse().setComplete(); // devuelve el error
    }
}
