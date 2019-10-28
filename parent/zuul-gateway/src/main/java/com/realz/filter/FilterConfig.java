package com.realz.filter;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FilterConfig {

	
	@Bean
	public RestTemplateFilter restTemplateFilter(RestTemplate restTemplate) {
		return new RestTemplateFilter(restTemplate); // 注入RestTemplate
	}
	

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
