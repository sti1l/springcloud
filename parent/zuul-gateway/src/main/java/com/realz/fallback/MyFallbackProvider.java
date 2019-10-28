package com.realz.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

/**
 * 回退的处理类
 *
 */
public class MyFallbackProvider implements ZuulFallbackProvider {

	// 返回路由的名称
	public String getRoute() {
		return "*";
	}

	// 回退触发时，返回默认的响应
	public ClientHttpResponse fallbackResponse() {
		return new ClientHttpResponse() {

			public InputStream getBody() throws IOException {
				return new ByteArrayInputStream("zull_fallback".getBytes());
			}

			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.TEXT_PLAIN);
				return headers;
			}

			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.OK;
			}

			public int getRawStatusCode() throws IOException {
				return 200;
			}

			public String getStatusText() throws IOException {
				return "OK";
			}

			public void close() {
				
			}
		};
	}
}
