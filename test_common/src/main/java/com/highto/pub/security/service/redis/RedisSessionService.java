package com.highto.pub.security.service.redis;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import com.highto.pub.security.domain.Authentication;
import com.highto.pub.security.service.SessionService;

public class RedisSessionService implements SessionService {

	private RedisTemplate<String, Map<String, Object>> redisTemplate;

	@Override
	public <T extends Serializable> void addAttribute(Authentication authentication, String name, T value) {
		redisTemplate.boundHashOps(authentication.getId()).put(name, value);
	}

	@Override
	public void removeAttribute(Authentication authentication, String name) {
		redisTemplate.boundHashOps(authentication.getId()).delete(name);
	}

	@Override
	public <T extends Serializable> T getAttribute(Authentication authentication, String name) {
		return (T) redisTemplate.boundHashOps(authentication.getId()).get(name);
	}

	@Override
	public <T extends Serializable> T getAttForAuth(Authentication authentication) {
		return getAttribute(authentication, authentication.getAuthAttKey());
	}

	public RedisTemplate<String, Map<String, Object>> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Map<String, Object>> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
