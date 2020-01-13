package com.realz.simple.elasticsearch.client;

import java.net.InetSocketAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ESTransportClient {

	private static TransportClient client;
	
	private static String IP ="127.0.0.1";

	/**
	 * 获取EsClient
	 * 
	 * @return
	 */
	public static TransportClient getInstance() {
		return client;
	}

	private ESTransportClient() {

	}

	static {
		// 通过setting对象指定集群配置信息, 配置的集群名
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch") // 设置ES实例的名称
				//.put("client.transport.sniff", true) // 自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
				.build();
		client = new PreBuiltTransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(IP, 9300)));
	}

}
