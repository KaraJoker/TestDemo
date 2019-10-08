package com.highto.framework.data.mongodb;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public abstract class MongodbDaoBase {

	protected MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * 根据id单属性更新
	 *
	 * @param <T>
	 * @param id
	 * @param field属性名
	 * @param input属性值
	 * @param entityClass实体类
	 * @return
	 * @throws Throwable异常
	 */
	protected <T> void updateSingleAttribute(String id, String field, Object input, Class<T> entityClass)
			throws Throwable {
		mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)), new Update().set(field, input), entityClass);
	}

	/**
	 * 根据id 多属性更新
	 *
	 * @param id
	 * @param inputs
	 * @param entityClass
	 * @param <T>
	 * @throws Throwable
	 */
	protected <T> void updateMultiAttributes(String id, Map<String, Object> inputs, Class<T> entityClass)
			throws Throwable {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		for (String field : inputs.keySet()) {
			update.set(field, inputs.get(field));
		}
		mongoTemplate.updateMulti(query, update, entityClass);
	}

	/**
	 * 根据多个查询条件 更新 一条记录的多个字段
	 *
	 * @param conditions
	 * @param inputs
	 * @param entityClass
	 * @param <T>
	 * @throws Throwable
	 */
	protected <T> void updateFirst(Map<String, Object> conditions, Map<String, Object> inputs, Class<T> entityClass)
			throws Throwable {
		Query query = new Query();
		for (String field : conditions.keySet()) {
			query.addCriteria(Criteria.where(field).is(conditions.get(field)));
		}

		Update update = new Update();
		for (String field : inputs.keySet()) {
			update.set(field, inputs.get(field));
		}
		mongoTemplate.updateFirst(query, update, entityClass);
	}

	/**
	 * 根据多个查询条件， 更新多条记录的 多个字段
	 *
	 * @param conditions
	 * @param inputs
	 * @param entityClass
	 * @param <T>
	 * @throws Throwable
	 */
	protected <T> void updateMulti(Map<String, Object> conditions, Map<String, Object> inputs, Class<T> entityClass)
			throws Throwable {
		Query query = new Query();
		for (String field : conditions.keySet()) {
			query.addCriteria(Criteria.where(field).is(conditions.get(field)));
		}

		Update update = new Update();
		for (String field : inputs.keySet()) {
			update.set(field, inputs.get(field));
		}
		mongoTemplate.updateMulti(query, update, entityClass);
	}

	/**
	 * n个条件（n>=0)查询（分页） （条件为String类型：模糊查询） （条件为非String类型：精确查询）
	 *
	 * @param paramMap参数map
	 * @param start起始记录位置
	 * @param size每页大小
	 * @param sort排序（根据某个属性按升序或降序排列）
	 * @param entityClass实体类
	 * @return
	 */
	protected <T> List<T> searchLike(Map<String, Object> paramMap, int start, int size, Sort sort,
			Class<T> entityClass) {
		Query queryBySearch = getQueryBySearch(paramMap, true);
		queryBySearch.skip(start);
		queryBySearch.limit(size);
		queryBySearch.with(sort);
		List<T> list = mongoTemplate.find(queryBySearch, entityClass);
		return list;
	}

	/**
	 * paramMap
	 *
	 * @param paramMap
	 * @param stringRegex
	 * @param param
	 *            范围指定查询字段
	 * @param startPoint
	 *            起始值
	 * @param endPoint
	 *            结束值
	 * @param start
	 * @param size
	 * @param sort
	 * @param entityClass
	 * @return
	 */
	protected <T> List<T> searchLike(Map<String, Object> paramMap, boolean stringRegex, String param, Long startPoint,
			Long endPoint, int start, int size, Sort sort, Class<T> entityClass) {
		Query queryBySearch = getQueryBySearch(paramMap, stringRegex);
		if (startPoint != null && endPoint != null) {
			queryBySearch.addCriteria(Criteria.where(param).gte(startPoint).lte(endPoint));
		}
		queryBySearch.skip(start);
		queryBySearch.limit(size);
		queryBySearch.with(sort);
		List<T> list = mongoTemplate.find(queryBySearch, entityClass);
		return list;
	}

	/**
	 * n个条件（n>=0)查询（不分页） （条件为String类型：模糊查询） （条件为非String类型：精确查询）
	 *
	 * @param paramMap参数map
	 * @param entityClass实体类
	 * @return
	 */
	protected <T> List<T> searchLike(Map<String, Object> paramMap, Class<T> entityClass) {
		Query queryBySearch = getQueryBySearch(paramMap, true);
		List<T> list = mongoTemplate.find(queryBySearch, entityClass);
		return list;
	}

	/**
	 * n个条件（n>=0)查询得到总记录数（分页/非分页） （条件为String类型：模糊查询） （条件为非String类型：精确查询）
	 *
	 * @param paramMap参数map
	 * @param entityClass实体类
	 * @return
	 */
	protected <T> int searchLikeCount(Map<String, Object> paramMap, Class<T> entityClass) {
		Query queryBySearch = getQueryBySearch(paramMap, true);
		int count = (int) mongoTemplate.count(queryBySearch, entityClass);
		return count;
	}

	/**
	 * @param paramMap
	 * @param stringRegex
	 * @param param
	 *            范围指定查询字段
	 * @param startPoint
	 *            起始值
	 * @param endPoint
	 *            结束值
	 * @param entityClass
	 * @return
	 */
	protected <T> int searchLikeCount(Map<String, Object> paramMap, boolean stringRegex, String param, Long startPoint,
			Long endPoint, Class<T> entityClass) {
		Query queryBySearch = getQueryBySearch(paramMap, stringRegex);
		if (startPoint != null && endPoint != null) {
			queryBySearch.addCriteria(Criteria.where(param).gte(startPoint).lte(endPoint));
		}
		int count = (int) mongoTemplate.count(queryBySearch, entityClass);
		return count;
	}

	private Query getQueryBySearch(Map<String, Object> paramMap, boolean stringRegex) {
		Query query = new Query();
		if (paramMap == null) {
			return query;
		}

		Criteria criteria = new Criteria();
		for (Entry<String, Object> kv : paramMap.entrySet()) {
			String key = kv.getKey();

			if (key != null) {
				Object value = kv.getValue();
				if (stringRegex && value != null && value instanceof String) {
					String v = value.toString();
					while (v.endsWith("\\")) {
						v = v.substring(0, v.length() + 1 - 2);
					}
					criteria.and(key).regex(v);
				} else {
					criteria.and(key).is(value);
				}
			}
		}

		query.addCriteria(criteria);
		return query;
	}

	protected ObjectId convertToObjectId(String id) {
		return new ObjectId(id);
	}
}
