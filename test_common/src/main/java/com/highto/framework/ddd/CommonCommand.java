package com.highto.framework.ddd;

import java.nio.ByteBuffer;
import java.util.List;

import com.highto.framework.nio.ByteBufferAble;
import com.highto.framework.nio.ByteBufferSerializer;

public class CommonCommand extends Command {

	private String type;

	private String method;

	private Object[] parameters;

	private int parameterGetIdx = 0;

	public CommonCommand() {
		super();
	}

	public CommonCommand(String type, String method, Object... parameters) {
		this.type = type;
		this.method = method;
		this.parameters = parameters;
	}

	public <T> T getParameter() {
		return (T) parameters[parameterGetIdx++];
	}

	@Override
	protected void doFillByByteBuffer(ByteBuffer bb) throws Throwable {
		type = ByteBufferSerializer.byteBufferToString(bb);
		method = ByteBufferSerializer.byteBufferToString(bb);
		int parameterLength = bb.getInt();
		if (parameterLength > 0) {
			parameters = new Object[parameterLength];
			for (int i = 0; i < parameterLength; i++) {
				parameters[i] = byteBufferToParameter(bb);
			}
		}
	}

	private Object byteBufferToParameter(ByteBuffer bb) throws Throwable {
		bb.mark();
		int parameterType = bb.getInt();
		if (parameterType == 1) {
			return ByteBufferSerializer.byteBufferToString(bb);
		} else if (parameterType == 2) {
			return ByteBufferSerializer.byteBufferToBoolean(bb);
		} else if (parameterType == 3) {
			return bb.getInt();
		} else if (parameterType == 4) {
			return bb.getLong();
		} else if (parameterType == 5) {
			return bb.getDouble();
		} else if (parameterType == 6) {
			return ByteBufferSerializer.byteBufferToList(bb);
		} else if (parameterType == 7) {
			return ByteBufferSerializer.byteBufferTointArray(bb);
		} else if (parameterType == 8) {
			String className = ByteBufferSerializer.byteBufferToString(bb);
			String value = ByteBufferSerializer.byteBufferToString(bb);
			Class oclass = Class.forName(className);
			return Enum.valueOf(oclass, value);
		} else if (parameterType == 9) {
			ByteBufferAble obj = ByteBufferSerializer.byteBufferToObj(bb);
			if (!(obj instanceof NullObj)) {
				return obj;
			} else {
				return null;
			}
		} else {
			bb.reset();
			return null;
		}
	}

	@Override
	protected void doToByteBuffer(ByteBuffer bb) throws Throwable {
		ByteBufferSerializer.stringToByteBuffer(type, bb);
		ByteBufferSerializer.stringToByteBuffer(method, bb);
		if (parameters != null) {
			bb.putInt(parameters.length);
			for (int i = 0; i < parameters.length; i++) {
				parameterToByteBuffer(parameters[i], bb);
			}
		} else {
			bb.putInt(0);
		}
	}

	private void parameterToByteBuffer(Object parameter, ByteBuffer bb) throws Throwable {
		if (parameter != null) {
			if (parameter instanceof String) {
				bb.putInt(1);
				ByteBufferSerializer.stringToByteBuffer((String) parameter, bb);
			} else if (parameter instanceof Boolean) {
				bb.putInt(2);
				ByteBufferSerializer.booleanToByteBuffer((Boolean) parameter, bb);
			} else if (parameter instanceof Integer) {
				bb.putInt(3);
				bb.putInt((int) parameter);
			} else if (parameter instanceof Long) {
				bb.putInt(4);
				bb.putLong((long) parameter);
			} else if (parameter instanceof Double) {
				bb.putInt(5);
				bb.putDouble((double) parameter);
			} else if (parameter instanceof List<?>) {
				bb.putInt(6);
				ByteBufferSerializer.listToByteBuffer((List<Object>) parameter, bb);
			} else if (parameter instanceof int[]) {
				bb.putInt(7);
				ByteBufferSerializer.intArrayToByteBuffer((int[]) parameter, bb);
			} else if (parameter instanceof Enum) {
				bb.putInt(8);
				ByteBufferSerializer.stringToByteBuffer(parameter.getClass().getName(), bb);
				ByteBufferSerializer.stringToByteBuffer(((Enum) parameter).name(), bb);
			} else if (parameter instanceof ByteBufferAble) {
				bb.putInt(9);
				ByteBufferSerializer.objToByteBuffer((ByteBufferAble) parameter, bb);
			} else {
			}
		} else {
			bb.putInt(9);
			ByteBufferSerializer.objToByteBuffer(new NullObj(), bb);
		}
	}

	public Class<?>[] getParameterTypes() {
		Class<?>[] types = new Class<?>[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			types[i] = parameters[i].getClass();
		}
		return types;
	}

	public String getType() {
		return type;
	}

	public String getMethod() {
		return method;
	}

	public Object[] getParameters() {
		return parameters;
	}

}
