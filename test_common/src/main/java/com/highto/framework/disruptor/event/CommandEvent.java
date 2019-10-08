package com.highto.framework.disruptor.event;

import com.highto.framework.ddd.Command;
import com.highto.framework.disruptor.Handler;

/**
 * 代表一个业务命令的事件
 * 
 * @author neo
 *
 */
public class CommandEvent {

	private Object attachment;

	private Command cmd;

	private Handler handler;

	private boolean snapshot = false;
	private boolean isRecordResult = false;

	public Object getAttachment() {
		return attachment;
	}

	public void setAttachment(Object attachment) {
		this.attachment = attachment;
	}

	public Command getCmd() {
		return cmd;
	}

	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}


	public boolean isSnapshot() {
		return snapshot;
	}

	public void setSnapshot(boolean snapshot) {
		this.snapshot = snapshot;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isRecordResult() {
		return isRecordResult;
	}

	public void setRecordResult(boolean recordResult) {
		isRecordResult = recordResult;
	}
}
