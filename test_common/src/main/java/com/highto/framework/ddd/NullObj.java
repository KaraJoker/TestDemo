package com.highto.framework.ddd;

import java.nio.ByteBuffer;

import com.highto.framework.nio.ByteBufferAble;

public class NullObj implements ByteBufferAble {

	@Override
	public void toByteBuffer(ByteBuffer bb) throws Throwable {
	}

	@Override
	public void fillByByteBuffer(ByteBuffer bb) throws Throwable {
	}

}