package com.highto.pub.codesys.dao;

import com.highto.pub.codesys.domain.IncreaseCodeGenerator;

/**
 * 自增编码生成器dao
 * 
 * @author wang xp
 * @date 2015-07-25
 */
public interface IncreaseCodeGeneratorDao {

	/**
	 * 根据名称获取Generator
	 * 
	 * @param name
	 * @return
	 */
	IncreaseCodeGenerator getGeneratorByName(String name);

	public void createIncreaseNumGenerate(IncreaseCodeGenerator ing);

	void updateGenerator(IncreaseCodeGenerator generator);

}
