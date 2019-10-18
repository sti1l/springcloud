package com.realz.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realz.dao.ReadAndWriteObjectDao;
import com.realz.entity.Person;

@RestController
public class TestController {
	
	@Resource
	private ReadAndWriteObjectDao rwd;
	
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

}
