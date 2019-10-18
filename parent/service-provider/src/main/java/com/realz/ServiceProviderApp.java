package com.realz;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableEurekaClient
@ComponentScan(basePackages = {"com.realz"})
public class ServiceProviderApp {
	public static void main(String[] args) {
		SpringApplication.run(ServiceProviderApp.class, args);
	}
}
