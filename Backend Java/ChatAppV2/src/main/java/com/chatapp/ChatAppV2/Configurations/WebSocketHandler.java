package com.chatapp.ChatAppV2.Configurations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    // @Override
    // public void handleMessage(WebSocketSession session, WebSocketMessage<?>
    // message) throws Exception {

    // }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        synchronized (sessions) {
            for (WebSocketSession wsSession : sessions) {
                if (wsSession.isOpen()) {
                    wsSession.sendMessage(message);
                }
            }
        }
    }

}
