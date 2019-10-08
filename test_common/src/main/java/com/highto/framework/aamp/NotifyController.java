package com.highto.framework.aamp;

/**
 * Created by tan on 2017/8/7.
 */
public interface NotifyController<T> extends Controller<T> {
	boolean dispatchNotify(T msg);
}

