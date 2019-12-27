package com.realz.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realz.common.GoodsComponent;
import com.realz.config.ESTransportClient;
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

	/**
	 * 测试批量创建索引
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {

		List<Map<String, Object>> good_list = rwd.getMapOfObjectsBySql(getFindGoodsAndKindinfoSql());
		
		TransportClient client = ESTransportClient.getInstance();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (Map<String, Object> map : good_list) {
			bulkRequest.add(
					(client.prepareIndex("realz", "goods").setId(String.valueOf(map.get("kindcode"))).setSource(map)));
		}
		BulkResponse actionGet = bulkRequest.execute().actionGet();
		
		return "创建成功, 新增" + good_list.size() + ", 用时" + actionGet.getTook() + "毫秒";
	}
	
	/**
	 * 批量新增文档
	 * @return
	 */
	@RequestMapping("/myindex")
	public String myindex() {

		List<Map<String, Object>> good_list = rwd.getMapOfObjectsBySql(GoodsComponent.getSql());

		TransportClient client = ESTransportClient.getInstance();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (Map<String, Object> map : good_list) {
			bulkRequest.add(
					(client.prepareIndex("myindex", "goods").setId(String.valueOf(map.get("goodscode"))).setSource(map)));
		}
		BulkResponse actionGet = bulkRequest.execute().actionGet();
		System.err.println(actionGet.buildFailureMessage());
		
		return "创建成功, 新增" + good_list.size() + ", 用时" + actionGet.getTook() + "毫秒";
	}
	
	@RequestMapping("/matchQuery")
	public ArrayList<Object> matchQuery() {
		TransportClient client = ESTransportClient.getInstance();
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("goodsname", "大闸蟹");
		builder.setQuery(queryBuilder);
		SearchResponse searchResponse = builder.get();
		/**
		 * 这是一个json 
		 * took：整个搜索请求花费多少毫秒
		 * shards：shards fail的条件，primary和replica全部挂掉，不影响其他shard。
               	# 默认情况，一个搜索请求，会发送到一个index的所有primary shard上；
               	# 当然，一个primary shard可能会有多个replica shard，所以请求也可能到其中某个replica shard 上。
           hits: 返回的结果
           	total: 本次搜索返回总条数
           	max_score: 本次搜索结果中最大的相关分数，每一条document对于search的相关度，越相关，source分数越大，排位越靠前
           	hits: 默认查询前十条数据，查询完整的数据，以_source降序排序
           
		 */
		System.err.println(searchResponse);
		
		// 总数(分页时用到)
		long totalHits = searchResponse.getHits().getTotalHits();
		System.err.println(totalHits);
		
		// 对检索结果要进行转换
		SearchHit[] hits = searchResponse.getHits().getHits();
		ArrayList<Object> arrayList = new ArrayList<Object>();
		for (SearchHit searchHit : hits) {
			arrayList.add(searchHit.getSource());
		}
		return arrayList;
	}
	
	
	/**
	 * 测试高亮: 标签是否奏效
	 * @return
	 */
	@RequestMapping("/testHighnight")
	public String testHighnight() {
		return "<h1>ssss</h1>";
	}
	
	
	
	
	/**
	 * 查询商品和规格
	 * @return sql
	 */
	public String getFindGoodsAndKindinfoSql() {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		builder.append("	g.goodsname, ");
		builder.append("	g.usefor, ");
		builder.append("	g.catetype, ");
		builder.append("	g.maingoodspic, ");
		builder.append("	g.bdirectsales, ");
		builder.append("	g.bshowprice, ");
		builder.append("	g.besale, ");
		builder.append("	g.buylink, ");
		builder.append("	g.goodssalestartdate, ");
		builder.append("	g.goodssaleenddate, ");
		builder.append("	g.bassignshipper, ");
		builder.append("	g.shippercode, ");
		builder.append("	g.blimitdisarea, ");
		builder.append("	g.regionnum, ");
		builder.append("	g.genaddr, ");
		builder.append("	g.validdate, ");
		builder.append("	g.traceshow, ");
		builder.append("	g.goodsmemo, ");
		builder.append("	g.tsales, ");
		builder.append("	g.tevaluates, ");
		builder.append("	g.tgoodevaluates, ");
		builder.append("	g.postagefree, ");
		builder.append("	g.postertemplatecode, ");
		builder.append("	g.intvideo, ");
		builder.append("	gk.id, ");
		builder.append("	gk.cmpycode, ");
		builder.append("	gk.qydm, ");
		builder.append("	gk.brandcode, ");
		builder.append("	gk.categorycode, ");
		builder.append("	gk.goodscode, ");
		builder.append("	gk.kindcode, ");
		builder.append("	gk.kindinfo, ");
		builder.append("	gk.kindcontent, ");
		builder.append("	gk.goodsprice, ");
		builder.append("	gk.goodsmallprice, ");
		builder.append("	gk.electsmallprice, ");
		builder.append("	gk.bexpresalegoods, ");
		builder.append("	gk.isgift, ");
		builder.append("	gk.bsale, ");
		builder.append("	gk.unshelvememo, ");
		builder.append("	gk.invcontrol, ");
		builder.append("	gk.showpos, ");
		builder.append("	gk.sales, ");
		builder.append("	gk.evaluates, ");
		builder.append("	gk.goodevaluates, ");
		builder.append("	gk.goodsweight, ");
		builder.append("	gk.tcategorycode, ");
		builder.append("	gk.pgoodscode, ");
		builder.append("	gk.goodssource, ");
		builder.append("	gk.pkindcode, ");
		builder.append("	gk.cooprice, ");
		builder.append("	gk.coostatus, ");
		builder.append("	gk.kindpic ");
		builder.append("FROM ");
		builder.append("	tb_goodskindinfo gk ");
		builder.append("LEFT JOIN tb_goods g ON g.goodscode = gk.goodscode; ");

		return builder.toString();
	}

}
