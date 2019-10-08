package com.highto.pub.codesys.dao;

import com.highto.pub.codesys.domain.HierarchyCodeGenerator;
import com.highto.pub.codesys.domain.HierarchyCodeNode;

/**
 * 编码生成器dao
 * 
 * @author wang xp
 * @date 2015-07-21
 */
public interface HierarchyCodeGeneratorDao {

	/**
	 * 根据名称获取Generator
	 * 
	 * @param name
	 * @return
	 */
	HierarchyCodeGenerator getGeneratorByName(String generatorName, int... structure);

	/**
	 * 删除层级code
	 * 
	 * @param docName
	 * @param currentCode
	 */
	void deleteHierarchyCode(String generatorName, String currentCode);

	/**
	 * 为当前节点添加下级code
	 * 
	 * @param codeStr
	 *            当前节点
	 */
	void updateHierarchyGenerator(String generatorName, HierarchyCodeNode newCodeNode);

	/**
	 * 注册新文档
	 * 
	 * @param doc
	 */
	public void createHierarchyGenerator(HierarchyCodeGenerator hcg);

}
