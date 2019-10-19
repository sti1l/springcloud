package com.realz;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZkServerApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ZkServerApp.class).web(true).run(args);
	}

}