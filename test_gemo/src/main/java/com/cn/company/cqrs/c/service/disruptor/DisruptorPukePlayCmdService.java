package com.cn.company.cqrs.c.service.disruptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.daboluo.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.daboluo.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.daboluo.cqrs.c.service.PukePlayCmdService;
import com.anbang.qipai.daboluo.cqrs.c.service.impl.PukePlayCmdServiceImpl;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;

@Component(value = "pukePlayCmdService")
public class DisruptorPukePlayCmdService extends DisruptorCmdServiceBase implements PukePlayCmdService {

	@Autowired
	private PukePlayCmdServiceImpl pukePlayCmdServiceImpl;

	@Override
	public PukeActionResult chupai(String playerId, String toudaoIndex, String zhongdaoIndex, String weidaoIndex,
			Long actionTime) throws Exception {
		CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "chupai", playerId, toudaoIndex,
				zhongdaoIndex, weidaoIndex, actionTime);
		DeferredResult<PukeActionResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
			PukeActionResult pukeActionResult = pukePlayCmdServiceImpl.chupai(cmd.getParameter(), cmd.getParameter(),
					cmd.getParameter(), cmd.getParameter(), cmd.getParameter());
			return pukeActionResult;
		});
		try {
			return result.getResult();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ReadyToNextPanResult readyToNextPan(String playerId) throws Exception {
		CommonCommand cmd = new CommonCommand(PukePlayCmdServiceImpl.class.getName(), "readyToNextPan", playerId);
		DeferredResult<ReadyToNextPanResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
			ReadyToNextPanResult readyToNextPanResult = pukePlayCmdServiceImpl.readyToNextPan(cmd.getParameter());
			return readyToNextPanResult;
		});
		try {
			return result.getResult();
		} catch (Exception e) {
			throw e;
		}
	}

}
