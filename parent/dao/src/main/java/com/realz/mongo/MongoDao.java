package com.realz.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

public interface MongoDao {

	public <T> List<T> findList(Integer skip, Integer limit, String sortStr, int sortType, Class<T> t);

	public <T> T findOne(String id, Class<T> t);

	public <T> List<T> findListByTerm(Integer skip, Integer limit, Criteria criteria, String sortStr, int sortType,
			Class<T> t);

	public <T> T findByTerm(Criteria criteria, Class<T> t);

	public <T> void insert(T t);

	public <T> void update(T entity);

	public MongoTemplate getMongoTemplate();

	public <T> void delete(Criteria criteria, Class<T> t);
	
	public <T> List<T> findAll(Class<T> t);
}
