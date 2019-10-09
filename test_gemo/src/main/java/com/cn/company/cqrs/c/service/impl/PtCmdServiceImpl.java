package com.cn.company.cqrs.c.service.impl;

import com.cn.company.cqrs.c.domain.UsMemberMap;
import com.cn.company.cqrs.c.service.PtCmdService;
import com.cn.company.cqrs.q.bean.PT;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PtCmdServiceImpl extends CmdServiceBase implements PtCmdService {

	@Override
	public void recycleWatch(PT pt) {
		UsMemberMap usMemberMap = singletonEntityRepository.getEntity(UsMemberMap.class);
		usMemberMap.recycleWatch(pt);
	}

	@Override
	public Map<String, Object> getWatch() {
		UsMemberMap usMemberMap = singletonEntityRepository.getEntity(UsMemberMap.class);
		return usMemberMap.getWatch();
	}
}
