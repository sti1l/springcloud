package com.realz.service.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCircuitBreaker
@EnableEurekaClient
@ComponentScan(basePackages = { "com.realz" })
public class ServiceProviderApp {
	public static void main(String[] args) {
		SpringApplication.run(ServiceProviderApp.class, args);
	}
}
