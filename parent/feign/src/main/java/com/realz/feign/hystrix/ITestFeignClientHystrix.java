package com.realz.feign.hystrix;

import org.springframework.stereotype.Component;

import com.realz.feign.ITestFeignClient;

@Component
public class ITestFeignClientHystrix implements ITestFeignClient{

	@Override
	public String hello() {
		// TODO Auto-generated method stub
		return "服务熔断了~";
	}

}
