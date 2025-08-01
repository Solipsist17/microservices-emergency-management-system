package com.microservice.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient // microservicio cliente de eureka
@EnableFeignClients
@SpringBootApplication
public class MicroserviceAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceAuthApplication.class, args);
	}

}
