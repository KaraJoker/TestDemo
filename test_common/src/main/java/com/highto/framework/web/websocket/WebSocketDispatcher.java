package com.highto.framework.web.websocket;

import com.google.gson.Gson;
import com.highto.framework.web.Dispatcher;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebSocketDispatcher implements WebSocketHandler {

    private Gson gson = new Gson();

    private ExecutorService executor = Executors.newCachedThreadPool();

    private Dispatcher dispatcher;

    public WebSocketDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        executor.submit(() -> {
            if (message.getPayloadLength() <= 0)
                return;
            dispatcher.dispatchMessage(message.getPayload().toString(), new WebSocketResponse(session, gson));
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
//        String message = "{closed:true}";
//        dispatcher.dispatchMessage(message, new WebSocketResponse(session, gson));
        // TODO Auto-generated method stub
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
