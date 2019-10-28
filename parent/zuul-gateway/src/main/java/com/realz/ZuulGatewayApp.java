package com.realz;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ZuulGatewayApp {
	public static void main(String[] args) {
		new SpringApplicationBuilder(ZuulGatewayApp.class).web(true).run(args);
	}
}
