package com.highto.framework.ddd;

import java.nio.ByteBuffer;

import com.highto.framework.nio.ByteBufferAble;
import com.highto.framework.nio.ByteBufferSerializer;

/**
 * 命令和其执行结果的一个封装。注意：并不是所有命令都会有结果。
 * 
 * @author neo
 *
 */
public class CRPair implements ByteBufferAble {

	private Command cmd;

	private ByteBufferAble cmdResult;

	public CRPair() {
	}

	public CRPair(Command cmd, ByteBufferAble cmdResult) {
		this.cmd = cmd;
		this.cmdResult = cmdResult;
	}

	@Override
	public void toByteBuffer(ByteBuffer bb) throws Throwable {
		ByteBufferSerializer.objToByteBuffer(cmd, bb);
		if (cmdResult != null) {
			ByteBufferSerializer.objToByteBuffer(cmdResult, bb);
		} else {
			ByteBufferSerializer.objToByteBuffer(new NullObj(), bb);
		}
	}

	@Override
	public void fillByByteBuffer(ByteBuffer bb) throws Throwable {
		cmd = ByteBufferSerializer.byteBufferToObj(bb);
		ByteBufferAble result = ByteBufferSerializer.byteBufferToObj(bb);
		if (!(result instanceof NullObj)) {
			cmdResult = result;
		} else {
			cmdResult = null;
		}
	}

}
