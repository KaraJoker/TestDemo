package com.highto.pub.security.service.jee;

import com.highto.pub.security.domain.Authentication;
import com.highto.pub.security.domain.jee.JEEAuthentication;
import com.highto.pub.security.service.AuthenticationService;

public class JEEAuthenticationService implements AuthenticationService {

	@Override
	public boolean isAuthenticationExistes(Authentication auth) {
		return ((JEEAuthentication) auth).getAttribute(auth.getAuthAttKey()) != null;
	}

}
