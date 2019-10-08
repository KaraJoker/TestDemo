package com.highto.pub.codesys.dao.mongodb;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.highto.pub.codesys.dao.IncreaseCodeGeneratorDao;
import com.highto.pub.codesys.dao.dto.IncreaseCodeDTO;
import com.highto.pub.codesys.domain.IncreaseCodeGenerator;

public class MongoIncreaseCodeGeneratorDao implements IncreaseCodeGeneratorDao {

	private Map<String, IncreaseCodeGenerator> generatorCache = new HashMap<String, IncreaseCodeGenerator>();

	private MongoTemplate mongoTemplate;

	public IncreaseCodeDTO getBeanByName(String name) {
		Query query = new Query();
		query.addCriteria(new Criteria("name").is(name));
		return mongoTemplate.findOne(query, IncreaseCodeDTO.class);
	}

	@Override
	public void createIncreaseNumGenerate(IncreaseCodeGenerator ing) {
		IncreaseCodeDTO increaseNumDto = new IncreaseCodeDTO();
		increaseNumDto.setName(ing.getName());
		increaseNumDto.setDigit(ing.getDigit());
		increaseNumDto.setCurrentIndex(ing.getCurrentIndex().longValue());
		mongoTemplate.insert(increaseNumDto);
	}

	@Override
	public IncreaseCodeGenerator getGeneratorByName(String name) {

		IncreaseCodeGenerator generator = generatorCache.get(name);
		if (generator != null) {
			return generator;
		} else {
			IncreaseCodeDTO dto = this.getBeanByName(name);
			if (dto != null) {
				generator = new IncreaseCodeGenerator(name, dto.getDigit(), dto.getCurrentIndex());
				generatorCache.put(name, generator);
			}
			return generator;
		}

	}

	@Override
	public void updateGenerator(IncreaseCodeGenerator generator) {
		Query query = new Query();
		query.addCriteria(new Criteria("name").is(generator.getName()));
		Update update = new Update();

		update.set("currentIndex", generator.getCurrentIndex().longValue());
		this.mongoTemplate.upsert(query, update, IncreaseCodeDTO.class);
	}

}
