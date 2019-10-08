package com.highto.pub.security.domain;

public class IdAuthenticationFactory {

	private String authAttKey;

	public IdAuthentication getAuthentication(String id) {
		IdAuthentication authentication = new IdAuthentication();
		authentication.setAuthAttKey(authAttKey);
		authentication.setId(id);
		return authentication;
	}

	public String getAuthAttKey() {
		return authAttKey;
	}

	public void setAuthAttKey(String authAttKey) {
		this.authAttKey = authAttKey;
	}

}
