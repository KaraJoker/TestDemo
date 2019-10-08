package com.highto.framework.aamp;

/**
 * Created by tan on 2017/7/25.
 */
public interface SocketMessageDecoder<T> {
	SocketMsgDecodeResult<T> decode(String msg) throws Exception;
}
