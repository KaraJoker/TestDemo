package com.highto.framework.ddd;

/**
 * 业务实体。一定能被唯一索引。
 * 
 * @author neo
 *
 */
public interface Entity {
	String getId();

	void setId(String id);
}
