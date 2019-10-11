package com.cn.company.cqrs.c.service.disruptor;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户相关的快照
 * 
 * @author neo
 *
 */
public class CoreSnapshot {
	private long createTime;
	private Map<Class<?>, Object> contentMap = new HashMap<>();

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Map<Class<?>, Object> getContentMap() {
		return contentMap;
	}

	public void setContentMap(Map<Class<?>, Object> contentMap) {
		this.contentMap = contentMap;
	}
}
