package com.highto.framework.aamp;

/**
 * Created by tan on 2017/8/7.
 */
public interface RequestController<T> extends Controller<T>{
	boolean dispatchRequest(T msg, Response response);
}
