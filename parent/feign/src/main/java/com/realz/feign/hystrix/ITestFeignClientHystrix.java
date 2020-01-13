package com.realz.feign.hystrix;

import org.springframework.stereotype.Component;

import com.realz.feign.ITestFeignClient;

@Component
public class ITestFeignClientHystrix implements ITestFeignClient{

	@Override
	public String hello() {
		return "服务熔断了~";
	}

	@Override
	public String getConfig() {
		return "服务熔断了~";
	}

}
