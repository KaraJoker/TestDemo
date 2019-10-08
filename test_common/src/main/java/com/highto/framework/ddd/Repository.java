package com.highto.framework.ddd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Repository<T extends Entity> {
	/**
	 * id索引
	 */
	private Map<String, T> index;

	/**
	 * 一维索引
	 */
	private Map<Object, T> odindex;

	/**
	 * 二维索引
	 */
	private Map<Object, Map<Object, T>> tdindex;

	/**
	 * 业务实体id生成器
	 */
	protected EntityIdGenerator entityIdGenerator;

	public Repository() {
		index = new ConcurrentHashMap<>();
		odindex = new ConcurrentHashMap<>();
		tdindex = new ConcurrentHashMap<>();
	}

	/**
	 * id索引 通过id查询
	 * 
	 * @param id
	 *            实体id
	 * @return
	 */
	public T findById(String id) {
		return index.get(id);
	}

	/**
	 * 获取所有
	 * 
	 * @return
	 */
	public List<T> findAll() {
		List<T> tList = new ArrayList<>();
		tList.addAll(index.values());
		return tList;
	}

	/**
	 * 保存 并添加索引
	 * 
	 * @param t
	 *            实体
	 */
	public void save(T t) {

		String id = t.getId();
		if (id == null || "".equals(id)) {
			if (entityIdGenerator != null) {
				id = entityIdGenerator.createId();
				t.setId(id);
			}
		}
		index.put(id, t);
		addIndex(t);
	}

	/**
	 * 删除 并删除索引
	 * 
	 * @param id
	 */
	public void remove(String id) {
		T t = findById(id);
		index.remove(id);
		if (t != null) {
			removeIndex(t);
		}
	}

	public void removeAll() {
		index.clear();
		odindex.clear();
		tdindex.clear();
	}

	/**
	 * 一维索引 查询
	 * 
	 * @param obj
	 * @return
	 */
	protected T findOne(Object obj) {
		return odindex.get(obj);
	}

	/**
	 * 添加一维索引
	 * 
	 * @param obj
	 *            索引
	 * @param t
	 *            实体
	 */
	protected void addOneDimensionalIndex(Object obj, T t) {
		odindex.put(obj, t);
	}

	/**
	 * 删除 一维索引
	 * 
	 * @param obj
	 */
	protected void removeOneDimensionalIndex(Object obj) {
		odindex.remove(obj);
	}

	/**
	 * 二维索引 通过两个参数查询
	 * 
	 * @param obj1
	 *            第一维索引
	 * @param obj2
	 *            第二维索引
	 * @return
	 */
	protected T findOne(Object obj1, Object obj2) {
		Map<Object, T> idx = tdindex.get(obj1);
		if (idx != null) {
			return idx.get(obj2);

		} else {
			return null;
		}

	}

	/**
	 * 二维索引 添加
	 * 
	 * @param obj1
	 *            第一维索引
	 * @param obj2
	 *            第二维索引
	 * @param t
	 */
	protected void addTwoDimensionalIndex(Object obj1, Object obj2, T t) {
		Map<Object, T> item = tdindex.get(obj1);
		if (item == null) {
			synchronized (this) {
				item = tdindex.get(obj1);
				if (item == null) {
					item = new ConcurrentHashMap<>();
					tdindex.put(obj1, item);
				}
			}
		}
		item.put(obj2, t);
	}

	/**
	 * 删除二维索引
	 * 
	 * @param obj1
	 * @param obj2
	 */
	protected void removeTwoDimensionalIndex(Object obj1, Object obj2) {
		Map<Object, T> item = tdindex.get(obj1);
		item.remove(obj2);
	}

	abstract protected void addIndex(T t);

	abstract protected void removeIndex(T t);

	public void fill(Repository<T> sourceRepository, EntityIdGenerator entityIdGenerator) {
		if (sourceRepository != null) {
			index.putAll(sourceRepository.index);
			odindex.putAll(sourceRepository.odindex);
			tdindex.putAll(sourceRepository.tdindex);

			for (Entry<Object, T> kv : this.odindex.entrySet()) {
				Object obj = kv.getKey();
				T odindexT = kv.getValue();
				T indexT = this.index.get(odindexT.getId());
				addOneDimensionalIndex(obj, indexT);
			}
			for (Entry<Object, Map<Object, T>> kv : this.tdindex.entrySet()) {
				Object obj1 = kv.getKey();
				Map<Object, T> item = kv.getValue();
				for (Entry<Object, T> itemKv : item.entrySet()) {
					Object obj2 = itemKv.getKey();
					T tdindexT = itemKv.getValue();
					T indexT = this.index.get(tdindexT.getId());
					addTwoDimensionalIndex(obj1, obj2, indexT);
				}
			}

			this.entityIdGenerator = sourceRepository.entityIdGenerator;
			if (this.entityIdGenerator == null) {
				this.entityIdGenerator = entityIdGenerator;
			}
		}
	}

	public Map<Object, T> getOdindex() {
		return odindex;
	}

	public void setOdindex(Map<Object, T> odindex) {
		this.odindex = odindex;
	}

	public Map<String, T> getIndex() {
		return index;
	}

	public void setIndex(Map<String, T> index) {
		this.index = index;
	}

	public Map<Object, Map<Object, T>> getTdindex() {
		return tdindex;
	}

	public void setTdindex(Map<Object, Map<Object, T>> tdindex) {
		this.tdindex = tdindex;
	}

	public EntityIdGenerator getEntityIdGenerator() {
		return entityIdGenerator;
	}

	public void setEntityIdGenerator(EntityIdGenerator entityIdGenerator) {
		this.entityIdGenerator = entityIdGenerator;
	}

}
