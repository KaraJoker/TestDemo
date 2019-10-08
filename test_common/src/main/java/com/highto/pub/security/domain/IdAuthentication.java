package com.highto.pub.security.domain;

public class IdAuthentication implements Authentication {

	private String id;

	private String authAttKey;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getAuthAttKey() {
		return authAttKey;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAuthAttKey(String authAttKey) {
		this.authAttKey = authAttKey;
	}

}
