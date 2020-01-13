package com.realz.simple.elasticsearch.example;

import java.util.ArrayList;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.realz.simple.elasticsearch.client.ESTransportClient;

/**
 * 全文查询
 * 
 * @author sti1l
 *
 */
public class FullTextQuery {

	private static TransportClient client;

	@Before
	public void MatchBefore() {
		client = ESTransportClient.getInstance();
	}

	/**
	 * match 查询会 解析 查询语句,
	 * 比如检索大闸蟹,他会检索出所有商品名带有 '蟹' 的数据
	 */
	@Test
	public void match_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("goodsname", "大闸蟹");
		builder.setQuery(queryBuilder);
		SearchResponse searchResponse = builder.get();
		/**
		 *这是一个json 
		 * took：整个搜索请求花费多少毫秒 
		 * shards：shards fail的条件（primary和replica全部挂掉），不影响其他shard。
		 * 	默认情况下来说，一个搜索请求，会打到一个index的所有primary shard上去，当然了，
		 * 	每个primary shard都可能会有一个或多个replic shard，所以请求也可以到primary shard的其中一个replica shard上去。
		 * timeout: 默认无timeout，latency平衡completeness，手动指定timeout，timeout查询执行机制
		 * hits: 返回的结果 
		 * 	total: 本次搜索返回总条数 
		 * 	max_score: 本次搜索结果中最大的相关分数，每一条document对于search的相关度，越相关，source分数越大，排位越靠前 
		 * 	hits: 默认查询前十条数据，查询完整的数据，以_source降序排序
		 */
		System.err.println(searchResponse);

		// 总数(分页时用到)
		long totalHits = searchResponse.getHits().getTotalHits();
		System.err.println(totalHits);

		SearchHit[] hits = searchResponse.getHits().getHits();
		// 对检索结果要进行转换
		ArrayList<Object> arrayList = new ArrayList<Object>();
		for (SearchHit searchHit : hits) {
			arrayList.add(searchHit.getSource());
			System.err.println(searchHit.getSource());
		}

	}

	/**
	 * match_phrase_query
	 * （1） 分词 后 所有 词 项 都要 出 现在 该 字段 中。 
	 * （2） 字段 中的 词 项 顺序 要 一致。
	 * 比如检索柏俊丞品牌, 他会检索出所有商品名为'柏俊丞品牌'的数据
	 */
	@Test
	public void match_phrase_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
		QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("goodsname", "柏俊丞品牌");
		builder.setQuery(queryBuilder);
		SearchResponse searchResponse = builder.get();
		SearchHit[] hits = searchResponse.getHits().getHits();
		System.err.println(searchResponse);
		for (SearchHit searchHit : hits) {
			System.err.println(searchHit.getSource());
		}
	}

	/**
	 * match_ phrase_ prefix 
	 * 和 match_ phrase 类似， 只不过 match_ phrase_ prefix 支持 最后 一个
	 * term 前缀 匹配
	 */
	@Test
	public void match_phrase_prefix_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
		QueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("goodsname", "大闸蟹");
		builder.setQuery(queryBuilder);
		SearchResponse searchResponse = builder.get();
		SearchHit[] hits = searchResponse.getHits().getHits();
		System.err.println(searchResponse);
		for (SearchHit searchHit : hits) {
			System.err.println(searchHit.getSource());
		}
	}

	/**
	 * multi_match_query
	 * 检索多个字段 策略 和  match_query 一样
	 */
	@Test
	public void multi_match_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("大闸蟹", "goodsname", "goodsmemo");
		builder.setQuery(queryBuilder);
		SearchResponse searchResponse = builder.get();
		SearchHit[] hits = searchResponse.getHits().getHits();
		System.err.println(searchResponse);
		for (SearchHit searchHit : hits) {
			System.err.println(searchHit.getSource());
		}
	}

	/**
	 * common_terms_query
	 * 是一 种在不牺牲 性能 的情况下 替代 停用词 提高 搜索 准确率 和 召回 率 的方案。
	 */
	@Test
	public void common_terms_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
		QueryBuilder queryBuilder = QueryBuilders.commonTermsQuery("goodsname", "大闸蟹礼品 一")
				.cutoffFrequency(Float.valueOf("0.001")).lowFreqOperator(Operator.OR);
		builder.setQuery(queryBuilder);
		SearchResponse searchResponse = builder.get();
		SearchHit[] hits = searchResponse.getHits().getHits();
		System.err.println(searchResponse);
		for (SearchHit searchHit : hits) {
			System.err.println(searchHit.getSource());
		}
	}

	/**
	 * query_string_query
	 * 在 一个 查询 语句 中 可以 使用 多个 特殊 条件 关键字（ 如： AND| OR| NOT） 对 多个 字段 进行 查询
	 * 类似于自己写sql
	 */
	@Test
	public void query_string_query() {
		SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
		QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("大闸蟹 OR 卡").field("goodsname");
		builder.setQuery(queryBuilder);
		SearchResponse searchResponse = builder.get();
		SearchHit[] hits = searchResponse.getHits().getHits();
		System.err.println(searchResponse);
		for (SearchHit searchHit : hits) {
			System.err.println(searchHit.getSource());
		}
	}
	
	

	@After
	public void MatchAfter() {
		// 注意这个连接用完了一定要关闭,不然 ElasticSearch 控制台会一直报错
		// java.io.IOException: 远程主机强迫关闭了一个现有的连接。
		client.close();
	}

}
