package com.highto.framework.aamp;

/**
 * Created by tan on 2017/8/9.
 */
public interface SocketMessageEncoder<S, T> {
	 T encode(ProtocolType type, S msg) throws Exception;
}
