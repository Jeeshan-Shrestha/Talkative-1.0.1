package com.chatapp.ChatAppV2.Configurations;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.chatapp.ChatAppV2.Models.ChatMessage;
import com.chatapp.ChatAppV2.Models.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            broadcastSystemMessage(username + " joined the chat", MessageType.JOIN);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            broadcastSystemMessage(username + " has left the chat", MessageType.LEAVE);
        }
    }

    // @Override
    // public void handleMessage(WebSocketSession session, WebSocketMessage<?>
    // message) throws Exception {

    // }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username = (String) session.getAttributes().get("username");
        final ChatMessage chatMessage = new ChatMessage("boka", message.getPayload(), MessageType.CHAT);
        String json = objectMapper.writeValueAsString(chatMessage);
        synchronized (sessions) {
            for (WebSocketSession wsSession : sessions) {
                if (wsSession.isOpen()) {
                    wsSession.sendMessage(new TextMessage(json));
                }
            }
        }
    }

    private void broadcastSystemMessage(String content, MessageType type) throws IOException {
        ChatMessage systemMessage = new ChatMessage("Server", content, type);
        String json = objectMapper.writeValueAsString(systemMessage);
        synchronized (sessions) {
            for (WebSocketSession wSession : sessions) {
                if (wSession.isOpen()) {
                    wSession.sendMessage(new TextMessage(json));
                }
            }
        }
    }

}
