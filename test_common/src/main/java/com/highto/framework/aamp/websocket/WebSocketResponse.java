package com.highto.framework.aamp.websocket;


import com.highto.framework.aamp.Response;
import com.highto.framework.notify.Notifier;
import com.highto.framework.notify.websocket.WebsocketNotifier;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketResponse implements Response {
	private WebSocketSession session;


	public WebSocketResponse(WebSocketSession session) {
		this.session = session;
	}

	@Override
	public void flush(byte[] msg) {
		TextMessage sendMessage = new TextMessage(msg);
		try {
			session.sendMessage(sendMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void flush(String msg) {
		flush(msg.getBytes());
	}

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	@Override
	public Notifier createNotifier() throws UnsupportedOperationException {
		WebsocketNotifier notifier = new WebsocketNotifier();
		notifier.setSession(session);
		return notifier;
	}

}
