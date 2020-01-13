package com.realz.simple.elasticsearch.example;

import java.util.ArrayList;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.realz.simple.elasticsearch.client.ESTransportClient;

/**
 * 多条件查询
 * 
 * @author sti1l
 *
 */
public class CompoundQuery {
	
	private static TransportClient client;

	@BeforeClass
	public static void MatchBefore() {
		client = ESTransportClient.getInstance();
	}

	/**
	 * bool_query
	 * must 文档 必须 匹配 must 选 项下 的 查询 条件， 相当于 逻辑 运算 的 AND。
	 * should 文档 可以 匹配 should 选 项下的 查询 条件 也可以 不匹配， 相当于 逻辑 运算 的 OR。 
	 * must_not 与 must 相反， 匹配 该 选 项下 的 查询 条件 的 文档不会 被 返回。 
	 * filter 和 must 一样， 匹配 filter 选 项下 的 查询 条件 的 文档 才会 被 返回， 但是 filter 不
	 * 	评分， 只 起到 过滤 功能。
	 */
	@Test
	public void bool_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
		QueryBuilder query1 = QueryBuilders.matchQuery("goodsname", "大闸蟹");
		QueryBuilder query2 = QueryBuilders.matchQuery("qydm", "2250");
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(query1).must(query2);
		builder.setQuery(queryBuilder);
		SearchResponse searchResponse = builder.get();

		System.err.println(searchResponse);

		SearchHit[] hits = searchResponse.getHits().getHits();
		// 对检索结果要进行转换
		ArrayList<Object> arrayList = new ArrayList<Object>();
		for (SearchHit searchHit : hits) {
			arrayList.add(searchHit.getSource());
			System.err.println(searchHit.getSource());
		}
	}

	@Test
	public void constant_score_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
		QueryBuilder query1 = QueryBuilders.matchQuery("goodsname", "大闸蟹");
		QueryBuilder query2 = QueryBuilders.matchQuery("qydm", "2250");
		QueryBuilder queryBuilder = QueryBuilders.constantScoreQuery(query1).boost((float) 1.2);
		builder.setQuery(queryBuilder);
		SearchResponse searchResponse = builder.get();

		System.err.println(searchResponse);

		SearchHit[] hits = searchResponse.getHits().getHits();
		// 对检索结果要进行转换
		ArrayList<Object> arrayList = new ArrayList<Object>();
		for (SearchHit searchHit : hits) {
			arrayList.add(searchHit.getSource());
			System.err.println(searchHit.getSource());
		}
	}

	/**
	 * dis_max query 与 bool query 有 一定 联系 也有 一定 区别， dis_ max query 支持 多 并发 查询， 可 返回
	 * 与 任意 查询 条件 子句 匹配 的 任何 文档 类型。 与 bool 查询 可以 将 所有 匹配 查询 的 分数 相结合 使 的 方式 不同， 
	 * dis_max 查询 只 使用 最佳 匹配 查询 条件 的 分数。
	 */
	@Test
	public void dis_max_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods").setSize(10000);
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
		QueryBuilder query1 = QueryBuilders.matchQuery("goodsname", "大闸蟹");
		QueryBuilder query2 = QueryBuilders.matchQuery("qydm", "2250");
		QueryBuilder queryBuilder = QueryBuilders.disMaxQuery().add(query1).add(query2).boost(1.1f).tieBreaker(0.7f);
		builder.setQuery(queryBuilder);
		SearchResponse searchResponse = builder.get();

		System.err.println(searchResponse);

		SearchHit[] hits = searchResponse.getHits().getHits();
		// 对检索结果要进行转换
		ArrayList<Object> arrayList = new ArrayList<Object>();
		for (SearchHit searchHit : hits) {
			arrayList.add(searchHit.getSource());
			System.err.println(searchHit.getSource());
		}
		// 我测试的时候发现评分过滤并不怎么好用
		System.err.println(searchResponse.getHits().getTotalHits());
		
		// 
	}
	
	// 停用了
	@Test
	public void indices_query() {
		
	}
	

	@AfterClass
	public static void MatchAfter() {
		// 注意这个连接用完了一定要关闭,不然 ElasticSearch 控制台会一直报错
		// java.io.IOException: 远程主机强迫关闭了一个现有的连接。
		client.close();
	}
}
