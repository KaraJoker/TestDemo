package com.highto.framework.disruptor.event.factory;

import com.highto.framework.disruptor.event.CommandEvent;
import com.lmax.disruptor.EventFactory;

public class CommandEventFactory implements EventFactory<CommandEvent> {

	@Override
	public CommandEvent newInstance() {
		return new CommandEvent();
	}

}
