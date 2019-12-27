package com.realz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realz.entity.mongodb.User;
import com.realz.mongo.MongoDao;

/**
 * 关系数据库 MongoDB的使用
 * 
 * @author sti1l
 *
 */
@RestController
public class TestMongoController {

	@Autowired
	private MongoDao dao;

	/**
	 * 测试新增
	 * 
	 * @return
	 */
	@RequestMapping("/insert")
	public String insert() {

		User data = new User();
		
		data.setAge(2);
		data.setName("realz2");
		data.setCompany("jht");
		dao.insert(data);

		return "保存成功 , id ：" + data.getId();
	}
	
	
	@RequestMapping("/findAll")
	public List<User> findAll() {

		return dao.findAll(User.class);
	}

}
