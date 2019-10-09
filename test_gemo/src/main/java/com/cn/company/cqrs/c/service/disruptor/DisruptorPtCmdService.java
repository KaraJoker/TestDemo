package com.cn.company.cqrs.c.service.disruptor;

import com.cn.company.cqrs.c.service.PtCmdService;
import com.cn.company.cqrs.c.service.impl.PtCmdServiceImpl;
import com.cn.company.cqrs.q.bean.PT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;

import java.util.Map;

@Component(value = "ptCmdService")
public class DisruptorPtCmdService extends DisruptorCmdServiceBase implements PtCmdService {

	@Autowired
	private PtCmdServiceImpl ptCmdServiceImpl;

	@Override
	public void recycleWatch(PT pt) {
		CommonCommand cmd = new CommonCommand(PtCmdServiceImpl.class.getName(), "recycleWatch", pt);
		DeferredResult<Object> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
			ptCmdServiceImpl.recycleWatch(cmd.getParameter());
			return null;
		});
		try {
			result.getResult();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> getWatch() {
		CommonCommand cmd = new CommonCommand(PtCmdServiceImpl.class.getName(), "getWatch");
		DeferredResult<Map<String, Object>> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
			return ptCmdServiceImpl.getWatch();
		});
		try {
			return  result.getResult();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
