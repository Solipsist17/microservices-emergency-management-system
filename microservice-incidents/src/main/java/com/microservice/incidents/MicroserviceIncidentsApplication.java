package com.microservice.incidents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceIncidentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceIncidentsApplication.class, args);
	}

}
