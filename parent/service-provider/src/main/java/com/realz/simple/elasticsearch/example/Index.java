package com.realz.simple.elasticsearch.example;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.realz.entity.Person;
import com.realz.simple.elasticsearch.client.ESTransportClient;
import com.realz.simple.elasticsearch.common.GoodsComponent;

/**
 * 索引创建
 * @author sti1l
 *
 */
public class Index {

	private TransportClient client = ESTransportClient.getInstance();

	/**
	 * 创建索引
	 */
	@Test
	public void createIndex() {
		client.admin().indices().prepareCreate("myindex").execute().actionGet();
		PutMappingRequest mapping = null;
		try {
			mapping = Requests.putMappingRequest("myindex").type("goods").source(GoodsComponent.getGoodsMapping());
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.admin().indices().putMapping(mapping).actionGet();
		System.err.println("索引创建成功");
	}

	/**
	 * 根据id查询
	 */
	@Test
	public void getById() {
		GetResponse getResponse = client.prepareGet("website", "blog", "AWuW0OA3PaQbZ9EXwRk9").get();
		System.err.println(getResponse.getSourceAsString());
	}

	/**
	 * 根据id更新
	 */
	@Test
	public void updateById() {
		XContentBuilder source = null;
		try {
			source = XContentFactory.jsonBuilder().startObject().field("title", "My second blog post UP").endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.prepareUpdate("website", "blog", "AWuW0OA3PaQbZ9EXwRk9").setDoc(source).get();
	}

	/**
	 * 单条件查询, 输入的查询内容是什么，就会按照什么去查询，不会解析查询内容，不对它分词。 
	 * 一般查询类型为: keyword
	 */
	@Test
	public void getByOneTerm() {
		SearchResponse response = client.prepareSearch("realz").setTypes("goods")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.matchQuery("goodsname", "用于"))
				.execute().actionGet();
		client.close();
		System.err.println(response);
	}

	/**
	 * 单个新增文档
	 */
	@Test
	public void add() {
		Person person = new Person();
		
		person.setId(100);
		person.setName("realz");
		person.setCompany("SIEMENS");
		person.setAge(100);

		JSONObject json = (JSONObject) JSONObject.toJSON(person);

		IndexResponse indexResponse2 = client.prepareIndex("website", "blog").setSource(json)
				.setId(person.getId().toString()).get();
		System.err.println(indexResponse2);
	}

	/**
	 * 批量新增文档
	 *  1.无映射(文档结构),根据字段属性自动新增
	 *  2.id相同时,更新
	 * 
	 * @return
	 */
	@Test
	public void addBulk() {
		// ES 识别三种数据形式 1.map 2.json 3.jsonString
		List<Map<String, Object>> good_list = null;

		TransportClient client = ESTransportClient.getInstance();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (Map<String, Object> map : good_list) {
			bulkRequest.add((client.prepareIndex("myindex", "goods").setId(String.valueOf(map.get("goodscode")))
					.setSource(map)));
		}
		BulkResponse actionGet = bulkRequest.execute().actionGet();
		System.err.println(actionGet.buildFailureMessage());
	}

	/**
	 * 获取总数
	 */
	@Test
	public void getNum() {
		SearchResponse resPonse = client.prepareSearch("realz").setTypes("goods")
				.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
		long totalHits = resPonse.getHits().getTotalHits();

		System.err.println(totalHits);
	}

	/**
	 * 分页查询
	 */
	@Test
	public void getPageQuery() {
		QueryBuilder query = QueryBuilders.matchAllQuery();
		SortBuilder<?> sort = SortBuilders.fieldSort("id")// 排序字段
				.order(SortOrder.DESC);// 升序或者降序
		SearchResponse response = client.prepareSearch("realz")// 索引名称
				.setTypes("goods")// type名称
				.setFrom(0) // 起始位置
				.setSize(1) // 条数 es最大分页条数是10000
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)// 设置查询类型，精确查询
				.setQuery(query)// 设置查询关键词
				.addSort(sort)// 设置排序规则
				.execute().actionGet();
		System.err.println(response);

		long totalHits = response.getHits().getTotalHits();

		System.err.println("数量: " + totalHits);
	}

}
