package com.highto.framework.http.jetty;

import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.client.util.StringContentProvider;

import com.highto.framework.http.HttpClient;
import com.highto.framework.http.HttpResponseContentProcessor;
import com.highto.framework.http.HttpSendParameters;

/**
 * 基于jetty client的HttpClient
 * 
 * @author zheng chengdong
 *
 */
public class JettyHttpClient implements HttpClient {

	private org.eclipse.jetty.client.HttpClient httpClient;

	public JettyHttpClient(org.eclipse.jetty.client.HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public String get(String url, String charset) throws Throwable {
		Request request = httpClient.newRequest(url);
		addGarnishedHeader(request);
		ContentResponse response;
		response = request.send();
		return new String(response.getContent(), charset);
	}

	@Override
	public String get(String url) throws Throwable {
		return get(url, "UTF-8");
	}

	@Override
	public void post(String url, HttpSendParameters body, HttpResponseContentProcessor processor) throws Throwable {
		Request request = httpClient.POST(url);
		addGarnishedHeader(request);
		request.content(new StringContentProvider(body.getParametersStr()));
		request.send(new BufferingResponseListener() {

			@Override
			public void onComplete(Result result) {
				try {
					if (result.getResponse().getStatus() == 200) {
						String responseStr = new String(getContent(), "UTF-8");
						processor.processContent(responseStr);
					} else {
						processor.processError(new Exception("http失败，返回状态码：" + result.getResponse().getStatus()));
					}
				} catch (Throwable e) {
					processor.processError(e);
				}
			}

		});
	}

	@Override
	public String post(String url, HttpSendParameters body) throws Throwable {
		Request request = httpClient.POST(url);
		addGarnishedHeader(request);
		request.content(new StringContentProvider(body.getParametersStr()));
		ContentResponse response = request.send();
		return new String(response.getContent(), "UTF-8");
	}

	private void addGarnishedHeader(Request request) {
		request.header("Connection", "keep-alive").header("Cache-Control", "max-age=0")
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.76 Safari/537.36")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Accept-Encoding", "gzip,deflate,sdch").header("Accept-Language", "zh-CN,zh;q=0.8");
	}

}
