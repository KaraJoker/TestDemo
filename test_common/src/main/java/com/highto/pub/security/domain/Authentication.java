package com.highto.pub.security.domain;

/**
 * 一个认证.代表着一次验证的通过,也代表着一次新的会话,所以也是一个session的标识
 * 
 * @author zhengchengdong
 *
 */
public interface Authentication {

	public String getId();

	/**
	 * 每个Authentication都有可能有一个附件。比如在一般的web系统中，登录后放入session的user对象。<br/>
	 * 这里给出这个附件在存储中的key。
	 * 
	 * @return
	 */
	public String getAuthAttKey();

}