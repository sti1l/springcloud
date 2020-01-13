package com.realz.simple.elasticsearch.common;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * ES 商品组件
 * @author JHT
 *
 */
public class GoodsComponent {
	
	public static XContentBuilder getGoodsMapping() throws IOException {
		return  XContentFactory.jsonBuilder()
				.startObject().startObject("properties")
				.startObject("id")
				.field("type", "integer")
				.endObject()
				.startObject("cmpycode")
				.field("type", "text")
				.endObject()
				.startObject("qydm")
				.field("type", "text")
				.endObject()
				.startObject("categorycode")
				.field("type", "text")
				.endObject()
				.startObject("brandcode")
				.field("type", "text")
				.endObject()
				.startObject("goodsname")
				.field("type", "text")
				.field("analyzer", "ik_max_word")
			 	.field("search_analyzer", "ik_max_word")
				.endObject()
				.startObject("usefor")
				.field("type", "text")
				.endObject()
				.startObject("catetype")
				.field("type", "short")
				.endObject()
				.startObject("maingoodspic")
				.field("type", "keyword")
				.endObject()
				.startObject("bdirectsales")
				.field("type", "short")
				.endObject()
				.startObject("bshowprice")
				.field("type", "short")
				.endObject()
				.startObject("besale")
				.field("type", "short")
				.endObject()
				.startObject("buylink")
				.field("type", "keyword")
				.endObject()
				.startObject("goodssalestartdate")
				.field("type", "text")
				.endObject()
				.startObject("goodssaleenddate")
				.field("type", "text")
				.endObject()
				.startObject("bassignshipper")
				.field("type", "short")
				.endObject()
				.startObject("shippercode")
				.field("type", "text")
				.endObject()
				.startObject("blimitdisarea")
				.field("type", "short")
				.endObject()
				.startObject("regionnum")
				.field("type", "integer")
				.endObject()
				.startObject("genaddr")
				.field("type",  "text")
				.field("analyzer", "ik_max_word")
			 	.field("search_analyzer", "ik_max_word")
				.endObject()
				.startObject("validdate")
				.field("type", "text")
				.field("analyzer", "ik_max_word")
			 	.field("search_analyzer", "ik_max_word")
				.endObject()
				.startObject("traceshow")
				.field("type", "short")
				.endObject()
				.startObject("postagefree")
				.field("type", "short")
				.endObject()
				.startObject("goodsmemo")
				.field("type", "text")
				.field("analyzer", "ik_max_word")
			 	.field("search_analyzer", "ik_max_word")
				.endObject()
				.startObject("tsales")
				.field("type", "integer")
				.endObject()
				.startObject("tevaluates")
				.field("type", "integer")
				.endObject()
				.startObject("tgoodevaluates")
				.field("type", "integer")
				.endObject()
				.startObject("operuname")
				.field("type", "text")
				.endObject()
				.startObject("operucode")
				.field("type", "text")
				.endObject()
				.startObject("isdelete")
				.field("type", "text")
				.endObject()
				.startObject("dataorigin")
				.field("type", "short")
				.endObject()
				.startObject("uid")
				.field("type", "text")
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
		builder.append("	id, ");
		builder.append("	cmpycode, ");
		builder.append("	qydm, ");
		builder.append("	categorycode, ");
		builder.append("	brandcode, ");
		builder.append("	goodscode, ");
		builder.append("	goodsname, ");
		builder.append("	usefor, ");
		builder.append("	catetype, ");
		builder.append("	maingoodspic, ");
		builder.append("	bdirectsales, ");
		builder.append("	bshowprice, ");
		builder.append("	besale, ");
		builder.append("	buylink, ");
		builder.append("	goodssalestartdate, ");
		builder.append("	goodssaleenddate, ");
		builder.append("	bassignshipper, ");
		builder.append("	shippercode, ");
		builder.append("	blimitdisarea, ");
		builder.append("	regionnum, ");
		builder.append("	genaddr, ");
		builder.append("	validdate, ");
		builder.append("	traceshow, ");
		builder.append("	postagefree, ");
		builder.append("	goodsmemo, ");
		builder.append("	tsales, ");
		builder.append("	tevaluates, ");
		builder.append("	tgoodevaluates, ");
		builder.append("	operuname, ");
		builder.append("	operucode, ");
		builder.append("	isdelete, ");
		builder.append("	dataorigin, ");
		builder.append("	uid ");
		builder.append("FROM ");
		builder.append("	tb_goods; ");

		return builder.toString();
		
	}
}
