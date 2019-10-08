package com.highto.framework.disruptor;

import com.highto.framework.nio.ByteBufferAble;

/**
 * Created by tan on 2016/8/23.
 */
public interface Handler {
	/**
	 * 回调函数
	 * @return
	 */
	ByteBufferAble handle();
}
