package com.realz.elasticsearch;

import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.realz.config.ESTransportClient;

/**
 * 高亮查询
 * @author sti1l
 *
 */
public class Higthlight {

	private static TransportClient client;

	@BeforeClass
	public static void MatchBefore() {
		client = ESTransportClient.getInstance();
	}

	/**
	 * 自定义标签高亮
	 */
	@Test
	public void highLigth() {

		try {
			SearchRequestBuilder builder = client.prepareSearch("myindex").setTypes("goods");
			builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);// 查询类型为精确查询
			QueryBuilder queryBuilder = QueryBuilders.matchQuery("goodsname", "大闸蟹");

			HighlightBuilder hBuilder = new HighlightBuilder();
			hBuilder.requireFieldMatch(false);
			hBuilder.field("goodsname");
			hBuilder.preTags("<strong>").postTags("</strong>");
			

			builder.highlighter(hBuilder);

			builder.setQuery(queryBuilder);
			SearchResponse searchResponse = builder.get();
			SearchHit[] hits = searchResponse.getHits().getHits();
			System.err.println(searchResponse);
			for (SearchHit searchHit : hits) {
				Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
				System.err.println(highlightFields);
				
				Text[] fragments1 = searchHit.getHighlightFields().get("goodsname").fragments();
				String goodsnameTmp1 = "";
				for (Text text : fragments1) {
					goodsnameTmp1 += text;
				}
				searchHit.getSource().put("goodsname", goodsnameTmp1);
				System.err.println(searchHit.getSource());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void MatchAfter() {
		// 注意这个连接用完了一定要关闭,不然 ElasticSearch 控制台会一直报错
		// java.io.IOException: 远程主机强迫关闭了一个现有的连接。
		client.close();
	}
}
