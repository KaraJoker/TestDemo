package com.highto.framework.disruptor;

public enum ThreadLocalSingleton {
	INSTANCE;
	private ThreadLocal threadLocal;

	private ThreadLocalSingleton() {
		this.threadLocal = new ThreadLocal();
	}

	public <T> ThreadLocal<T> getThreadLocal() {
		return threadLocal;
	}
}
