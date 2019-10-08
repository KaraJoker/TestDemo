package com.highto.pub.security.service;

import java.io.Serializable;

import com.highto.pub.security.domain.Authentication;

/**
 * 主要提供session数据的存取服务
 * 
 * @author zhengchengdong
 *
 */
public interface SessionService {

	/**
	 * 向session中存一个key-value的attribute
	 * 
	 * @param authentication
	 *            session标识
	 * @param name
	 *            attribute的key
	 * @param value
	 *            attribute的value
	 */
	<T extends Serializable> void addAttribute(Authentication authentication, String name, T value);

	/**
	 * 移除session中一个attribute
	 * 
	 * @param authentication
	 * @param name
	 */
	void removeAttribute(Authentication authentication, String name);

	/**
	 * 获取session中一个attribute
	 * 
	 * @param authentication
	 * @param name
	 */
	<T extends Serializable> T getAttribute(Authentication authentication, String name);

	/**
	 * 得到authentication的附件
	 * 
	 * @param authentication
	 * @return
	 */
	<T extends Serializable> T getAttForAuth(Authentication authentication);

}
