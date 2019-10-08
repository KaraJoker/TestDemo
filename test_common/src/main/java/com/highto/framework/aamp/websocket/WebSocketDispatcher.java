package com.highto.framework.aamp.websocket;


import com.highto.framework.aamp.DispatcherController;
import com.highto.framework.aamp.SocketMessageDecoder;
import com.highto.framework.aamp.SocketMsgDecodeResult;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class WebSocketDispatcher<T> implements WebSocketHandler, SocketMessageDecoder<T> {

	private ExecutorService executor = Executors.newCachedThreadPool();
	protected DispatcherController<T> dispatcher;

	public WebSocketDispatcher(DispatcherController<T> dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		executor.submit(() -> {
			if (message.getPayloadLength() <= 0)
				return;
			try {
				SocketMsgDecodeResult<T> result = decode(message.getPayload().toString());
				dispatcher.dispatch(result, new WebSocketResponse(session));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		//TODO
//		String message = "{closed:true}";
//		SocketMsgDecodeResult<T> result = decode(message.getPayload().toString());
//		dispatchMessage(new WebSocketResponse(session, gson), message);
//		dispatcher.dispatch(result, new WebSocketResponse(session));
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
