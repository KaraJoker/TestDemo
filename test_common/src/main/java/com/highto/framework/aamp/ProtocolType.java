package com.highto.framework.aamp;

/**
 * Created by tan on 2017/8/4.
 */
public enum ProtocolType {
	/**
	 * 请求。 需要响应. 且编码对应。
	 */
	request(0),

	/**
	 * 通知。
	 */
	notify(1);

	private int value;

	ProtocolType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
