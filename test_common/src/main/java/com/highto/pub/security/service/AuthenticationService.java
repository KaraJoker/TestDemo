package com.highto.pub.security.service;

import com.highto.pub.security.domain.Authentication;

/**
 * 认证相关服务
 * 
 * @author zheng chengdong
 *
 */
public interface AuthenticationService {
	/**
	 * 是否存在认证（认证验证）。通常用于验证是否已登录
	 * 
	 * @param auth
	 * @return
	 */
	boolean isAuthenticationExistes(Authentication auth);
}
