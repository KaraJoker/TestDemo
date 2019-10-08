package com.highto.pub.security.service.jee;

import java.io.Serializable;

import com.highto.pub.security.domain.Authentication;
import com.highto.pub.security.domain.jee.JEEAuthentication;
import com.highto.pub.security.service.SessionService;

public class JEESessionService implements SessionService {

	@Override
	public <T extends Serializable> void addAttribute(Authentication authentication, String name, T value) {
		((JEEAuthentication) authentication).setAttribute(name, value);

	}

	@Override
	public void removeAttribute(Authentication authentication, String name) {
		((JEEAuthentication) authentication).removeAttribute(name);
	}

	@Override
	public <T extends Serializable> T getAttribute(Authentication authentication, String name) {
		return (T) ((JEEAuthentication) authentication).getAttribute(name);
	}

	@Override
	public <T extends Serializable> T getAttForAuth(Authentication authentication) {
		return getAttribute(authentication, authentication.getAuthAttKey());
	}

}
