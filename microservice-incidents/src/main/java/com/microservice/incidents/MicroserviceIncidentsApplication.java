package com.microservice.incidents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.microservice.incidents.client")
@SpringBootApplication
public class MicroserviceIncidentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceIncidentsApplication.class, args);
	}

}
