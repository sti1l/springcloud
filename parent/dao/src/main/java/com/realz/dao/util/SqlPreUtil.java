package com.realz.dao.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 * sql预编译辅助类
 * @author yinlijun
 * @since 2016/01/11
 * 重载了注入参数的方法，就是参数列表有一个Object...preParameters的方法
 * 修改：陈敏
 */
public class SqlPreUtil {
	
	/**
	 * 为query进行参数赋值
	 * @param query
	 * @param preParameters
	 */
	public static void setValForQuery(Query query, List<Object> preParameters){
		if (preParameters != null) {
			int i = 0;//表示第几个？
			for (Object object : preParameters) {
				if (object instanceof String) {
					query.setString(i, (String)object);
				} else if (object instanceof Short) {
					query.setShort(i, (Short) object);
				} else if (object instanceof Integer) {
					query.setInteger(i, (Integer) object);
				} else if (object instanceof Short) {
					query.setShort(i, (Short) object);
				} else if (object instanceof Double) {
					query.setDouble(i, (Double) object);
				} else if (object instanceof Float) {
					query.setFloat(i, (Float) object);
				} else if (object instanceof Timestamp) {
					query.setTimestamp(i, (Timestamp) object);
				}else if (object instanceof Date) {
					query.setTimestamp(i, (Date) object);
				}else if(object == null){
					query.setDate(i, null);
				}else if (object instanceof BigDecimal) {
					query.setBigDecimal(i, (BigDecimal)object);
				}else {
					System.out.println("请补充数据类型");
				}
				i++;
			}
		}
	}
	
	
	
	public static void setValForSqlQuery(SQLQuery sqlQuery, List<Object> preParameters){
		if (preParameters != null) {
			int i = 0;//表示第几个？
			for (Object object : preParameters) {
				if (object instanceof String) {
					sqlQuery.setString(i, (String)object);
				} else if (object instanceof Short) {
					sqlQuery.setShort(i, (Short) object);
				} else if (object instanceof Integer) {
					sqlQuery.setInteger(i, (Integer) object);
				} else if (object instanceof Short) {
					sqlQuery.setShort(i, (Short) object);
				} else if (object instanceof Double) {
					sqlQuery.setDouble(i, (Double) object);
				} else if (object instanceof Float) {
					sqlQuery.setFloat(i, (Float) object);
				} else if (object instanceof Timestamp) {
					sqlQuery.setTimestamp(i, (Timestamp) object);
				}else if (object instanceof Date) {
					sqlQuery.setTimestamp(i, (Date) object);
				}else if(object == null){
					sqlQuery.setDate(i, null);
				}else {
					System.out.println("请补充数据类型");
				}
				i++;
			}
		}
	}
	
}
