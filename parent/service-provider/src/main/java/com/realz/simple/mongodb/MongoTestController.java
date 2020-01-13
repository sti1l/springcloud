package com.realz.simple.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realz.dao.bean.PageBean;
import com.realz.entity.mongodb.User;
import com.realz.mongo.MongoDao;

/**
 * 关系数据库 MongoDB的使用Demo
 * 
 * @author sti1l
 *
 */
@RestController
public class MongoTestController {

	@Autowired
	private MongoDao dao;

	/**
	 * 增
	 * 
	 * @return
	 */
	public @RequestMapping("/insert") String insert() {

		User data = new User();
		// data.setId("1");
		data.setAge(2);
		data.setName("sti11");
		data.setCompany("jht");
		dao.insert(data);

		return "保存成功 , id ：" + data.getId();
	}

	/**
	 * 查所有
	 * 
	 * @return
	 */
	public @RequestMapping("/findAll") List<User> findAll() {

		return dao.findAll(User.class);
	}

	/**
	 * 更
	 * 
	 * @return
	 */
	public @RequestMapping("update") List<User> update() {
		User user = dao.findOne("1", User.class);

		System.err.println(user);
		user.setName("realz");
		dao.update(user);

		return dao.findAll(User.class);
	}

	/**
	 * 删
	 */
	public @RequestMapping("deleteAll") void deleteAll() {
		MongoTemplate mongoTemplate = dao.getMongoTemplate();
		mongoTemplate.findAllAndRemove(new Query(), User.class);
	}
	
	/**
	 * 分页查
	 * @return
	 */
	public @RequestMapping("pageFind") List<User> pageFind() {
		PageBean pageBean = new PageBean();
		List<User> findList = dao.findList((pageBean.getCurpage() - 1) * pageBean.getPagesize(), 
				pageBean.getPagesize(), "id", 0, User.class);
		return findList;
		
	}

}
