package com.realz.simple.mysql;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realz.dao.ReadAndWriteObjectDao;
import com.realz.entity.Person;

/**
 * 关系型数据库Controller 类
 * @author sti1l
 *
 */
@RestController
public class TestDaoController {

	@Resource
	private ReadAndWriteObjectDao rwd;
	
	@Value("${name}")
	private String name;
	
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping("/savePerson")
	public String savePerson() {
		Person person = new Person();
		
		person.setAge(1);
		person.setCompany("苏州百捷");
		person.setName("realz");

		rwd.addObject(person);

		Integer id = person.getId();
		return "person创建成功 id为: " + id;
	}

	/**
	 * 测试从配置中心的文件中获取
	 * @return
	 */
	@RequestMapping("/getConfig")
	public String getConfig() {
		return name;
	}
	
}
