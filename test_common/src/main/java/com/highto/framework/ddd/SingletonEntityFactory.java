package com.highto.framework.ddd;

public interface SingletonEntityFactory {

	<T> T createNew(Class<T> type);

}
