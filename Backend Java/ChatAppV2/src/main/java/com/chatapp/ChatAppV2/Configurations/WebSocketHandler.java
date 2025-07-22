package com.chatapp.ChatAppV2.Configurations;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.chatapp.ChatAppV2.Models.ChatMessage;
import com.chatapp.ChatAppV2.Models.MessageType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = (String) session.getAttributes().get("username");
        sessions.put(username, session);
        if (username != null) {
            broadcastSystemMessage(username + " joined the chat", MessageType.JOIN);
            // System.out.println(username + " joined the chat");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("username");
        sessions.remove(username);
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

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message.getPayload());
        String content = node.get("content").asText();
        String receiver = node.has("receiver") ? node.get("receiver").asText() : null;

        final ChatMessage chatMessage = new ChatMessage(username, content, MessageType.CHAT);
        String json = objectMapper.writeValueAsString(chatMessage);

        if (receiver != null){
            WebSocketSession receiverSession = sessions.get(receiver);
            if (receiverSession != null && receiverSession.isOpen()){
                receiverSession.sendMessage(new TextMessage(json));
            }
        }

        else{
            for (WebSocketSession wsSession : sessions.values()) {
                if (wsSession.isOpen()) {
                    wsSession.sendMessage(new TextMessage(json));
                }
            }
        
    }
    }

    private void broadcastSystemMessage(String content, MessageType type) throws IOException {
        ChatMessage systemMessage = new ChatMessage("Server", content, type);
        String json = objectMapper.writeValueAsString(systemMessage);
            for (WebSocketSession wSession : sessions.values()) {
                if (wSession.isOpen()) {
                    wSession.sendMessage(new TextMessage(json));
                    System.out.println(json);
                }
            }
    }

}
