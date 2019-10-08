package com.highto.pub.common.jsonrpc;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.highto.framework.http.HttpClientFactory;
import com.highto.framework.http.HttpSendParameters;
import com.highto.framework.http.jetty.JettyHttpClientFactory;

public abstract class JsonRpcServiceBase {

	private Logger logger = Logger.getLogger(JsonRpcServiceBase.class);

	private String serverAddress;

	private String requestSuffix = "";

	private Gson gson = new Gson();

	private HttpClientFactory httpClientFactory;

	protected String jsonRpcRequestId;

	public JsonRpcServiceBase() {
		try {
			httpClientFactory = new JettyHttpClientFactory();
		} catch (Throwable e) {
			logger.error("create JettyHttpClientFactory error", e);
		}
	}

	protected String post(Object... params) throws Throwable {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		String method = stackTraceElement.getMethodName();
		JsonRpcRequestFB jsonRpcRequestFB = new JsonRpcRequestFB();
		if (jsonRpcRequestId != null) {
			jsonRpcRequestFB.setId(jsonRpcRequestId);
		}
		jsonRpcRequestFB.setMethod(method);
		jsonRpcRequestFB.setParams(toJsonStrArray(params));
		HttpSendParameters body = new HttpSendParameters();
		body.addEntity("JRR", gson.toJson(jsonRpcRequestFB));

		String resContent = httpClientFactory.getHttpClient().post(serverAddress + "/"
				+ stackTraceElement.getClassName().replaceAll("\\.", "/") + "/" + method + requestSuffix, body);
		logger.info("post收到远程响应：" + resContent);
		return resContent;
	}

	protected String get(Object... params) throws Throwable {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		String method = stackTraceElement.getMethodName();
		JsonRpcRequestFB jsonRpcRequestFB = new JsonRpcRequestFB();
		if (jsonRpcRequestId != null) {
			jsonRpcRequestFB.setId(jsonRpcRequestId);
		}
		jsonRpcRequestFB.setMethod(method);
		jsonRpcRequestFB.setParams(toJsonStrArray(params));
		String resContent = httpClientFactory.getHttpClient().get(serverAddress + "/" + stackTraceElement.getClassName()
				+ "/" + method + requestSuffix + "?JRR=" + gson.toJson(jsonRpcRequestFB));
		logger.info("get收到远程响应：" + resContent);
		return resContent;
	}

	private String[] toJsonStrArray(Object... params) {
		String[] jsonStrArray = new String[params.length];
		for (int i = 0; i < params.length; i++) {
			jsonStrArray[i] = gson.toJson(params[i]);
		}
		return jsonStrArray;
	}

	protected <T> T getResObj(String resCtt, Class<T> cls) {
		JsonRpcResponseVO vo = gson.fromJson(resCtt, JsonRpcResponseVO.class);
		return gson.fromJson(gson.toJson(vo.getResult()), cls);
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public String getRequestSuffix() {
		return requestSuffix;
	}

	public void setRequestSuffix(String requestSuffix) {
		this.requestSuffix = requestSuffix;
	}

	public String getJsonRpcRequestId() {
		return jsonRpcRequestId;
	}

	public void setJsonRpcRequestId(String jsonRpcRequestId) {
		this.jsonRpcRequestId = jsonRpcRequestId;
	}

}
