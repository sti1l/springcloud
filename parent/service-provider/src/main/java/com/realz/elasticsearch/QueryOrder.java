package com.realz.elasticsearch;

import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.realz.config.ESTransportClient;

/**
 * 排序
 * @author sti1l
 *
 */
public class QueryOrder {
	
	private static TransportClient client;

	@BeforeClass
	public static void MatchBefore() {
		client = ESTransportClient.getInstance();
	}
	
	/**
	 * 多字段排序
	 */
	@Test
	public void order_by_multi_fields() {
		MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
		
		FieldSortBuilder order_by_id_asc = SortBuilders.fieldSort("id").order(SortOrder.ASC);
		FieldSortBuilder order_by_tsales_desc = SortBuilders.fieldSort("tsales").order(SortOrder.DESC);
		
		SearchResponse searchResponse = client.prepareSearch("myindex").setTypes("goods").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		.addSort(order_by_id_asc).addSort(order_by_tsales_desc).setQuery(query).get();
		
		SearchHit[] hits = searchResponse.getHits().getHits();
		for (SearchHit searchHit : hits) {
			Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
			System.err.println(sourceAsMap);
		}
		
		
	}
	
	@AfterClass
	public static void MatchAfter() {
		// 注意这个连接用完了一定要关闭,不然 ElasticSearch 控制台会一直报错
		// java.io.IOException: 远程主机强迫关闭了一个现有的连接。
		client.close();
		System.err.println("relaz: 连接已关闭");
	}
}
