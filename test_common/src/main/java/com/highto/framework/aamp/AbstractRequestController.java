package com.highto.framework.aamp;

/**
 * Created by tan on 2017/8/10.
 */
public abstract class AbstractRequestController<T> implements RequestController<T> {
	public abstract Object takeSequence(T msg);

	public abstract byte[] fillSequence(Object msg, Object sequence);
}
