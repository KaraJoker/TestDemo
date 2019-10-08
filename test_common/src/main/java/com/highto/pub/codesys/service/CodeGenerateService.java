package com.highto.pub.codesys.service;

/**
 * 编码生成器service
 * 
 * @author wang xp
 * @date 2015-07-21
 */
public interface CodeGenerateService {

	/**
	 * 生成层级code
	 * 
	 * @param name
	 * @param currentCode
	 * @return
	 */
	String getNextHierarchyCode(String name, String currentCode, int... structure) throws Throwable;

	/**
	 * 生成层级code，不保存到库
	 * 
	 * @param name
	 * @param currentCode
	 * @return
	 */
	String getVirtualNextHierarchyCode(String name, String currentCode, int... structure);

	/**
	 * 删除层级code
	 * 
	 * @param docName
	 * @param currentCode
	 */
	void deleteHierarchyCode(String docName, String currentCode, int... structure) throws Throwable;

	/**
	 * 获取增长字符串
	 * 
	 * @param name
	 * @param digit
	 *            10进制位数
	 * @return
	 */
	String getNextIncreaseNum(String generatorName, int digit);

}
