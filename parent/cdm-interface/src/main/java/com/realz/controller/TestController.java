package com.realz.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realz.feign.ITestFeignClient;

/**
 * 测试feign调用服务类
 * @author sti1l
 *
 */
@RestController
public class TestController {
	
	@Resource
	ITestFeignClient testFeignClient;
	
	@RequestMapping("/hello")
	public String hello() {
		
		String hello = testFeignClient.hello();
		return hello;
	}
	
	
	@RequestMapping("/getConfig")
	public String getConfig() {
		
		String config = testFeignClient.getConfig();
		return config;
	}
	
}
