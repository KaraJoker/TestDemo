package com.highto.framework.notify.websocket;

import com.highto.framework.notify.AbstractNotifier;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebsocketNotifier extends AbstractNotifier {

	private WebSocketSession session;

	@Override
	public void doNotify(byte[] msg) {
		TextMessage sendMessage = new TextMessage(msg);
		try {
			session.sendMessage(sendMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		session.close();
	}

	@Override
	public boolean isConnected() {
		return session.isOpen();
	}

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) {
		this.session = session;
	}

}
