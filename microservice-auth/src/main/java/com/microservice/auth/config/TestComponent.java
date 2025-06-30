package com.microservice.auth.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TestComponent {
    @PostConstruct
    public void init() {
        System.out.println("===== TEST COMPONENT INICIADO =====");
    }
}
