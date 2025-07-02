package com.microservice.patrols;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.microservice.patrols.client")
@SpringBootApplication
public class MicroservicePatrolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicePatrolsApplication.class, args);
	}

}
