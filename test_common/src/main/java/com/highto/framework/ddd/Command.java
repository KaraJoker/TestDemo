package com.highto.framework.ddd;

import java.nio.ByteBuffer;

import com.highto.framework.nio.ByteBufferAble;

/**
 * 业务命令。此类型主要是为了把领域服务方法的参数抽象成一个可序列化/记录的单元。
 * 
 * @author neo
 *
 */
public abstract class Command implements ByteBufferAble {

	private long cmdTime = System.currentTimeMillis();

	public long getCmdTime() {
		return cmdTime;
	}

	@Override
	public void fillByByteBuffer(ByteBuffer bb) throws Throwable {
		cmdTime = bb.getLong();
		doFillByByteBuffer(bb);
	}

	protected abstract void doFillByByteBuffer(ByteBuffer bb) throws Throwable;

	@Override
	public void toByteBuffer(ByteBuffer bb) throws Throwable {
		bb.putLong(cmdTime);
		doToByteBuffer(bb);
	}

	protected abstract void doToByteBuffer(ByteBuffer bb) throws Throwable;

}
