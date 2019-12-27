package com.realz.mongo;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository("mongoDao")
public class MongoDaoImpl implements MongoDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 查询分页数据
	 * 
	 * @param skip     跳过记录数
	 * @param limit    查询记录数
	 * @param sortStr  排序字段
	 * @param sortType 排序类型 1：倒序 0:正序
	 * @param t        查询记录的实体
	 */
	@Override
	public <T> List<T> findList(Integer skip, Integer limit, String sortStr, int sortType, Class<T> t) {
		Query query = new Query();
		Direction direction = Direction.ASC;
		switch (sortType) {
		case 1:
			direction = Direction.DESC;
			break;
		default:
			break;
		}
		query.with(new Sort(new Order(direction, sortStr)));
		if (skip != null) {
			query.skip(skip).limit(limit);
		}
		return this.mongoTemplate.find(query, t);
	}

	/**
	 * 通过查询条件查询单个结果集
	 * 
	 * @param criteria 查询条件
	 * @param t        查询的实体
	 */
	public <T> T findByTerm(Criteria criteria, Class<T> t) {
		Query query = new Query();
		query.addCriteria(criteria);
		return this.mongoTemplate.findOne(query, t);
	}

	/**
	 * 查询分页数据
	 * 
	 * @param skip     跳过记录数
	 * @param limit    查询记录数
	 * @param sortStr  排序字段
	 * @param criteria 查询条件
	 * @param sortType 排序类型 1：倒序 0:正序
	 * @param t        查询记录的实体
	 */
	@Override
	public <T> List<T> findListByTerm(Integer skip, Integer limit, Criteria criteria, String sortStr, int sortType,
			Class<T> t) {
		Query query = new Query();
		query.addCriteria(criteria);
		Direction direction = Direction.ASC;
		switch (sortType) {
		case 1:
			direction = Direction.DESC;
			break;
		default:
			break;
		}
		query.with(new Sort(new Order(direction, sortStr)));
		if (skip != null) {
			query.skip(skip).limit(limit);
		}
		return this.mongoTemplate.find(query, t);
	}

	/**
	 * 通过id获取对象
	 * 
	 * @param id
	 * @param t  查询的实体
	 */
	@Override
	public <T> T findOne(String id, Class<T> t) {
		Query query = new Query();
		query.addCriteria(new Criteria("_id").is(id));
		return this.mongoTemplate.findOne(query, t);
	}

	/**
	 * 插入数据
	 * 
	 * @param t 插入的实体
	 */
	@Override
	public <T> void insert(T t) {
		this.mongoTemplate.insert(t);
	}

	/**
	 * 更新对象
	 * 
	 * @param t 更新的实体必须含id
	 */
	@Override
	public <T> void update(T t) {
		try {
			Query query = new Query();
			Class tc = t.getClass();
			query.addCriteria(new Criteria("_id").is(tc.getDeclaredMethod("getId").invoke(t)));
			Update update = new Update();
			Field[] fields = tc.getDeclaredFields();
			for (Field field : fields) {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), tc);
				Method getMethod = pd.getReadMethod();
				update.set(field.getName(), getMethod.invoke(t));
			}
			this.mongoTemplate.updateFirst(query, update, t.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public MongoTemplate getMongoTemplate() {
		return this.mongoTemplate;
	}

	@Override
	public <T> void delete(Criteria criteria, Class<T> t) {
		Query query = new Query();
		query.addCriteria(criteria);
		this.mongoTemplate.findAndRemove(query, t);
	}

	@Override
	public <T> List<T> findAll(Class<T> t) {
		return mongoTemplate.findAll(t);
	}

}
