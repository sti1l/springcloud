package com.realz.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.realz.feign.hystrix.ITestFeignClientHystrix;

@FeignClient(name="service-provider", fallback = ITestFeignClientHystrix.class)
public interface ITestFeignClient {
	
	@RequestMapping("/hello")
	public String hello();
	
	/**
	 * 获取配置name
	 * @return
	 */
	@RequestMapping("/getConfig")
	public String getConfig();

}
