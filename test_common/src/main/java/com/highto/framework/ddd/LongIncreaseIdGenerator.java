package com.highto.framework.ddd;

public class LongIncreaseIdGenerator implements EntityIdGenerator {

	private long id;

	@Override
	public String createId() {
		return ++id + "";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
