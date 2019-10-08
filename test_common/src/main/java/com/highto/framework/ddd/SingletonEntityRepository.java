package com.highto.framework.ddd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonEntityRepository {

	private SingletonEntityFactory entityFactory;

	private Map<Class<?>, Object> entities = new ConcurrentHashMap<>();

	public SingletonEntityRepository() {

	}

	public <T> T getEntity(Class<T> type) {
		T entity = (T) entities.get(type);
		if (entity != null) {
			return entity;
		} else {
			synchronized (this) {
				entity = (T) entities.get(type);
				if (entity == null) {
					entity = entityFactory.createNew(type);
					if (entity != null) {
						entities.put(type, entity);
					}
				}
				return entity;
			}
		}
	}

	public boolean hasEntity(Class<?> type) {
		return entities.containsKey(type);
	}

	public void fill(SingletonEntityRepository sourceRepository) {
		entities.putAll(sourceRepository.getEntities());
	}

	public <T> void putEntity(T entity) {
		entities.put(entity.getClass(), entity);
	}

	public SingletonEntityFactory getEntityFactory() {
		return entityFactory;
	}

	public void setEntityFactory(SingletonEntityFactory entityFactory) {
		this.entityFactory = entityFactory;
	}

	public Map<Class<?>, Object> getEntities() {
		return entities;
	}

	public void setEntities(Map<Class<?>, Object> entities) {
		this.entities = entities;
	}

}
