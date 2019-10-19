package com.realz.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realz.dao.ReadAndWriteObjectDao;
import com.realz.entity.Person;

@RestController
public class TestController {
	
	@Resource
	private ReadAndWriteObjectDao rwd;
	
	@Value("${name}")
	private String name;
	
	@RequestMapping("/hello")
	public String hello() {
		Person person = new Person();
		person.setAge(1);
		person.setCompany("苏州百捷");
		person.setName("realz");
		
		rwd.addObject(person);
		
		Integer id = person.getId();
		return "person创建成功 id为: " + id;
	}
	
	// 测试从配置文件中获取
	@RequestMapping("/getConfig")
	public String getConfig() {
		return name;
	}

}
