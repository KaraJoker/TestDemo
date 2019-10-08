package com.highto.pub.security.domain.jee;

import javax.servlet.http.HttpServletRequest;

import com.highto.pub.security.domain.Authentication;

public class JEEAuthentication implements Authentication {

	private HttpServletRequest request;

	private String authKeyName;

	public JEEAuthentication(HttpServletRequest request, String authKeyName) {
		this.request = request;
		this.authKeyName = authKeyName;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getAuthAttKey() {
		return authKeyName;
	}

	public void setAuthKeyName(String authKeyName) {
		this.authKeyName = authKeyName;
	}

	public void setAttribute(String name, Object value) {
		request.getSession().setAttribute(name, value);
	}

	public void removeAttribute(String name) {
		request.getSession().removeAttribute(name);
	}

	public Object getAttribute(String name) {
		return request.getSession().getAttribute(name);
	}

	@Override
	public String getId() {
		return null;
	}

}
