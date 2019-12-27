package com.realz.elasticsearch;

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

import com.realz.config.ESTransportClient;

/**
 * 词项查询
 * 
 * @author sti1l
 *
 */
public class TermQuery {

	private static TransportClient client;

	@BeforeClass
	public static void MatchBefore() {
		client = ESTransportClient.getInstance();
	}

	/**
	 * 单词项查询
	 */
	@Test
	public void term_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
		QueryBuilder queryBuilder = QueryBuilders.termQuery("goodscode", "76dcc3dcd70741e7803961352557c850");
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
	 * 范围查询 注意: 词项查询和范围查询不可共用 gt: > 大于（greater than） lt: < 小于（less than） gte: >=
	 * 大于或等于（greater than or equal to） lte: <= 小于或等于（less than or equal to）
	 */
	@Test
	public void range_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询

		QueryBuilder query_match = QueryBuilders.matchQuery("goodsname", "葡萄");

		QueryBuilder query_range = QueryBuilders.rangeQuery("id").gt(4).lt(8); // 7<id<8

		builder.setQuery(QueryBuilders.boolQuery().must(query_match).must(query_range));
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
	 * exists 查询 会 返回 字段 中 至少 有一个 非 空 值 的 文档。
	 */
	@Test
	public void exists_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询

		QueryBuilder exists_query = QueryBuilders.existsQuery("goodsname");

		builder.setQuery(QueryBuilders.boolQuery().must(exists_query));
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
	 * 通配符查询, wildcard 查询 的 查询 性能 也不 是 很高， 需要 消耗 较多 的 CPU 资源。(不建议使用)
	 */
	@Test
	public void wildcard_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询

		QueryBuilder query_range = QueryBuilders.wildcardQuery("goodsname", "大闸蟹*");

		builder.setQuery(QueryBuilders.boolQuery().must(query_range));
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
	 * type query 用于 查询 具有 指定 类型 的 文档 和setTypes();作用类似
	 */
	@Test
	public void type_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询

		QueryBuilder type_query = QueryBuilders.typeQuery("goods");

		builder.setQuery(QueryBuilders.boolQuery().must(type_query));
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
	 * ids query 用于 查询 具有 指定 id 的 文档。 类型 是 可选 的， 也可以 省略， 也可以 接受 一个 数组。 如果 未指定 任何 类型，
	 * 则 会 搜索 索引 中的 所有 类型。
	 */
	@Test
	public void ids_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询

		QueryBuilder ids_query = QueryBuilders.idsQuery("goods").addIds("1", "2", "44edf68a2f39439a8bcd7bf6643ea285");

		builder.setQuery(QueryBuilders.boolQuery().must(ids_query));
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

	

	@AfterClass
	public static void MatchAfter() {
		// 注意这个连接用完了一定要关闭,不然 ElasticSearch 控制台会一直报错
		// java.io.IOException: 远程主机强迫关闭了一个现有的连接。
		client.close();
	}
}
