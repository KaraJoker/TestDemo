package com.highto.framework.http;

/**
 * 返回内容（字符串）处理。用于异步
 * 
 * @author zheng chengdong
 *
 */
public interface HttpResponseContentProcessor {

	void processContent(String content);

	void processError(Throwable err);

}
