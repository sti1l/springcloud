package com.realz.common;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * 规格组件, 测试
 * @author sti1l
 *
 */
public class KindinfoComponent {
	
	/**
	 * 获取映射
	 * @return
	 * @throws IOException
	 */
	public static XContentBuilder getMapping() throws IOException {
		return  XContentFactory.jsonBuilder()
				.startObject().startObject("properties")
				// 规格编号
				.startObject("kindcode")
				.field("type", "text")
				.endObject()
				// 规格名称
				.startObject("kindinfo")
				.field("type", "text")
				.field("analyzer", "ik_max_word")
			 	.field("search_analyzer", "ik_max_word")
				.endObject()
				// 规格详情
				.startObject("kindcontent")
				.field("type", "text")
				.field("analyzer", "ik_max_word")
			 	.field("search_analyzer", "ik_max_word")
				.endObject()
				// 价格
				.startObject("price")
				.field("type", "scaled_float")
				.endObject()
				// 图片
				.startObject("pic")
				.field("type", "ip")
				.endObject()
				.endObject()
				.endObject()
				.endObject();
	}
	
	
	/**
	 * 获取sql
	 * @return
	 */
	public static String getSql() {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		builder.append("	kindcode, ");
		builder.append("	kindinfo, ");
		builder.append("	kindcontent, ");
		builder.append("	price, ");
		builder.append("	pic ");
		builder.append("FROM ");
		builder.append("	tb_goodskindinfo; ");

		return builder.toString();
		
	}

}
