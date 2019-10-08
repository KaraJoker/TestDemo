package com.highto.framework.http;

public interface HttpClient {

	String post(String url, HttpSendParameters body) throws Throwable;

	void post(String url, HttpSendParameters body, HttpResponseContentProcessor processor) throws Throwable;

	/**
	 * 发起同步 http get 请求，返回响应的文本内容。UTF-8编码。
	 * 
	 * @param url
	 *            请求地址，包含请求参数。
	 * 
	 * @return 响应的文本内容
	 * @throws Throwable
	 */
	String get(String url) throws Throwable;

	/**
	 * 发起同步 http get 请求，返回响应的文本内容。
	 * 
	 * @param url
	 *            请求地址，包含请求参数。
	 * 
	 * @param charset
	 *            字符编码
	 * @return 响应的文本内容
	 * @throws Throwable
	 */
	String get(String url, String charset) throws Throwable;

}
