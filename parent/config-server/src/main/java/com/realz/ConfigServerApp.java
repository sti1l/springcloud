package com.realz;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringCloudApplication
public class ConfigServerApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigServerApp.class).web(true).run(args);
	}

}
