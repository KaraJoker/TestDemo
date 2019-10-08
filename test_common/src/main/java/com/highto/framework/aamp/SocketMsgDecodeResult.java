package com.highto.framework.aamp;

/**
 * Created by tan on 2017/8/7.
 */
public class SocketMsgDecodeResult<T> {
	private Integer type;
	private T msg;

	public SocketMsgDecodeResult(Integer type, T msg) {
		this.type = type;
		this.msg = msg;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public T getMsg() {
		return msg;
	}

	public void setMsg(T msg) {
		this.msg = msg;
	}
}
