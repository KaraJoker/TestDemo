package com.highto.framework.web.websocket;

import com.google.gson.Gson;
import com.highto.framework.notify.Notifier;
import com.highto.framework.notify.websocket.WebsocketNotifier;
import com.highto.framework.web.Response;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketResponse implements Response {
	private WebSocketSession session;
	private Gson gson;

	public WebSocketResponse(WebSocketSession session, Gson gson) {
		this.session = session;
		this.gson = gson;
	}

	@Override
	public <T> void flush(T response) {
		TextMessage sendMessage = new TextMessage(gson.toJson(response).getBytes());
		try {
			session.sendMessage(sendMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
