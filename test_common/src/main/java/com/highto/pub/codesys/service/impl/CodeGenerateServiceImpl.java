package com.highto.pub.codesys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.highto.pub.codesys.dao.HierarchyCodeGeneratorDao;
import com.highto.pub.codesys.dao.IncreaseCodeGeneratorDao;
import com.highto.pub.codesys.domain.HierarchyCodeGenerator;
import com.highto.pub.codesys.domain.HierarchyCodeNode;
import com.highto.pub.codesys.domain.IncreaseCodeGenerator;
import com.highto.pub.codesys.service.CodeGenerateService;

public class CodeGenerateServiceImpl implements CodeGenerateService {

	@Autowired
	private HierarchyCodeGeneratorDao hierarchyCodeGeneratorDao;

	@Autowired
	private IncreaseCodeGeneratorDao increaseCodeGeneratorDao;

	@Override
	public String getNextHierarchyCode(String generatorName, String currentCode, int... structure) throws Throwable {
		HierarchyCodeGenerator hcg = hierarchyCodeGeneratorDao.getGeneratorByName(generatorName);
		if (hcg == null) {
			hcg = new HierarchyCodeGenerator(generatorName, structure);
			hierarchyCodeGeneratorDao.createHierarchyGenerator(hcg);
		}
		HierarchyCodeNode codeNode = hcg.getHierarchyCode(currentCode, true);
		hierarchyCodeGeneratorDao.updateHierarchyGenerator(generatorName, codeNode);
		return codeNode.getCode();
	}

	@Override
	public String getVirtualNextHierarchyCode(String generatorName, String currentCode, int... structure) {
		HierarchyCodeGenerator hcg = hierarchyCodeGeneratorDao.getGeneratorByName(generatorName);
		if (hcg == null) {
			hcg = new HierarchyCodeGenerator(generatorName, structure);
			hierarchyCodeGeneratorDao.createHierarchyGenerator(hcg);
		}
		String code = "";
		try {
			HierarchyCodeNode codeNode = hcg.getHierarchyCode(currentCode, false);
			code = codeNode.getCode();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return code;
	}

	@Override
	public void deleteHierarchyCode(String generatorName, String currentCode, int... structure) throws Throwable {
		HierarchyCodeGenerator hcg = hierarchyCodeGeneratorDao.getGeneratorByName(generatorName);
		if (hcg == null) {
			hcg = new HierarchyCodeGenerator(generatorName, structure);
			hierarchyCodeGeneratorDao.createHierarchyGenerator(hcg);
		}
		hcg.deleteHierarchyCode(currentCode);
		hierarchyCodeGeneratorDao.deleteHierarchyCode(generatorName, currentCode);
	}

	@Override
	public String getNextIncreaseNum(String generatorName, int digit) {
		IncreaseCodeGenerator generator = increaseCodeGeneratorDao.getGeneratorByName(generatorName);
		if (generator == null) {
			generator = new IncreaseCodeGenerator(generatorName, digit);
			increaseCodeGeneratorDao.createIncreaseNumGenerate(generator);
		}
		String code = generator.getIncreaseCode();
		increaseCodeGeneratorDao.updateGenerator(generator);
		return code;
	}

	public void setIncreaseCodeGeneratorDao(IncreaseCodeGeneratorDao increaseCodeGeneratorDao) {
		this.increaseCodeGeneratorDao = increaseCodeGeneratorDao;
	}

	public void setHierarchyCodeGeneratorDao(HierarchyCodeGeneratorDao hierarchyCodeGeneratorDao) {
		this.hierarchyCodeGeneratorDao = hierarchyCodeGeneratorDao;
	}

}
