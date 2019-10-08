package com.highto.pub.security.domain.jee;

import javax.servlet.http.HttpServletRequest;

public class JEEAuthenticationFactory {

	private String authAttKey;

	public JEEAuthentication getAuthentication(HttpServletRequest request) {
		return new JEEAuthentication(request, authAttKey);
	}

	public String getAuthAttKey() {
		return authAttKey;
	}

	public void setAuthAttKey(String authAttKey) {
		this.authAttKey = authAttKey;
	}

}
