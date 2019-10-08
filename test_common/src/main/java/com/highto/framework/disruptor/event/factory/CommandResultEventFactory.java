package com.highto.framework.disruptor.event.factory;

import com.highto.framework.disruptor.event.CommandResultEvent;
import com.lmax.disruptor.EventFactory;

public class CommandResultEventFactory implements EventFactory<CommandResultEvent> {

	@Override
	public CommandResultEvent newInstance() {
		return new CommandResultEvent();
	}

}
