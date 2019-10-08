package com.highto.pub.common.jsonrpc;

public class JsonRpcResponseVO {

	private String id = "";

	private String jsonrpc = "2.0";

	private Object result;

	public JsonRpcResponseVO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public JsonRpcResponseVO(Object result) {
		this.result = result;
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
