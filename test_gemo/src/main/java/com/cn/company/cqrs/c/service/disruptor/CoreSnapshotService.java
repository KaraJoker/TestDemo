package com.cn.company.cqrs.c.service.disruptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoreSnapshotService {

	@Autowired
	private DisruptorFactory disruptorFactory;

	public void makeSnapshot() {
		disruptorFactory.getCoreCmdDisruptor().publishEvent((event, sequence) -> event.setSnapshot(true));
	}

}