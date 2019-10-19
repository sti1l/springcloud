package com.realz.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realz.feign.ITestFeignClient;

@RestController
public class TestController {
	
	@Resource
	ITestFeignClient testFeignClient;
	
	@RequestMapping("/hello")
	public String hello() {
		
		String hello = testFeignClient.hello();
		return hello;
	}
	
}
