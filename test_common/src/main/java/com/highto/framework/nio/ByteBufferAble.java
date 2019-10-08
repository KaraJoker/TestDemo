package com.highto.framework.nio;

import java.nio.ByteBuffer;

/**
 * 可通过ByteBuffer序列化的
 * 
 * @author zheng chengdong
 *
 */
public interface ByteBufferAble {
	/**
	 * 序列化到ByteBuffer<br/>
	 * 实现类需定义数据格式
	 * 
	 * @param bb
	 * @throws Throwable
	 */
	void toByteBuffer(ByteBuffer bb) throws Throwable;

	/**
	 * 通过ByteBuffer来填充对象中的值<br/>
	 * 实现类需定义数据格式
	 * 
	 * @param bb
	 * @throws Throwable
	 */
	void fillByByteBuffer(ByteBuffer bb) throws Throwable;

}
