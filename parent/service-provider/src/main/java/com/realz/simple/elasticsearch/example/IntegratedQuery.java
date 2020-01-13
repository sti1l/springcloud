package com.realz.simple.elasticsearch.example;

import java.util.ArrayList;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.realz.dao.bean.PageBean;
import com.realz.simple.elasticsearch.client.ESTransportClient;

/**
 * 分页查询
 * @author sti1l
 *
 */
public class IntegratedQuery {

	private static TransportClient client;

	@BeforeClass
	public static void MatchBefore() {
		client = ESTransportClient.getInstance();
	}

	/**
	 * 分页/排序/多条件
	 */
	public static void getPageQuery() {

		PageBean pagebean = new PageBean();

		int start = (pagebean.getCurpage() - 1) * pagebean.getPagesize();
		int size = pagebean.getPagesize();

		QueryBuilder query = QueryBuilders.matchAllQuery();
		FieldSortBuilder sort = SortBuilders.fieldSort("id")// 排序字段
				.order(SortOrder.DESC);// 升序或者降序
		SearchResponse response = client.prepareSearch("myindex") // 索引名称
				.setTypes("mytype") // type名称
				.setFrom(start) // 起始位置
				.setSize(size) // 条数 es最大分页条数是10000
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH) // 设置查询类型，精确查询
				.setQuery(query) // 设置查询关键词
				.addSort(sort)// 设置排序规则
				.execute().actionGet();
		System.err.println(response);

		Long totalNum = response.getHits().getTotalHits();
		// 处理pagebean
		pagebean.setTotalNum(totalNum.intValue());
		pagebean.updatePage();
		// 处理结果
		SearchHit[] hits = response.getHits().getHits();
		ArrayList<Map<String, Object>> data = new ArrayList<>();
		for (SearchHit searchHit : hits) {
			data.add(searchHit.getSource());
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
