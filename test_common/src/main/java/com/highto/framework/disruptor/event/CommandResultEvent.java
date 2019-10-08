package com.highto.framework.disruptor.event;

import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.Command;

/**
 * 代表一个业务命令执行结果的事件
 * 
 * @author neo
 *
 */
public class CommandResultEvent {

	private Command cmd;

	private Object attachment;

	private Object result;

	private DeferredResult<?> deferredResult;

	public Command getCmd() {
		return cmd;
	}

	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}

	public Object getAttachment() {
		return attachment;
	}

	public void setAttachment(Object attachment) {
		this.attachment = attachment;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public DeferredResult<?> getDeferredResult() {
		return deferredResult;
	}

	public void setDeferredResult(DeferredResult<?> deferredResult) {
		this.deferredResult = deferredResult;
	}
}
