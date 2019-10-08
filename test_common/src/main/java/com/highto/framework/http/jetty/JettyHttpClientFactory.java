package com.highto.framework.http.jetty;

import java.util.concurrent.Executors;

import org.eclipse.jetty.util.HttpCookieStore;

import com.highto.framework.executor.ExpiredTaskExecutor;
import com.highto.framework.http.HttpClient;
import com.highto.framework.http.HttpClientFactory;

public class JettyHttpClientFactory implements HttpClientFactory {

	private HttpClient httpClient;

	public JettyHttpClientFactory() throws Throwable {
		org.eclipse.jetty.client.HttpClient jettyClient = new org.eclipse.jetty.client.HttpClient();
		jettyClient.setTCPNoDelay(true);
		jettyClient.setMaxConnectionsPerDestination(512);// 太大会把系统tcp连接占光
		jettyClient.setMaxRequestsQueuedPerDestination(Integer.MAX_VALUE >>> 4);
		jettyClient.setCookieStore(new HttpCookieStore.Empty());
		jettyClient.setFollowRedirects(false);
		jettyClient.setExecutor(new ExpiredTaskExecutor(Executors.newCachedThreadPool(), 10,
				task -> task.getClass().toString().contains("ReadCallback")));
		jettyClient.start();
		httpClient = new JettyHttpClient(jettyClient);
	}

	@Override
	public HttpClient getHttpClient() {
		return httpClient;
	}

}
