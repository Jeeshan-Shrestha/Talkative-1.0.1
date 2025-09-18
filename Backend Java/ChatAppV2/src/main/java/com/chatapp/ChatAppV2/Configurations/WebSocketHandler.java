package com.chatapp.ChatAppV2.Configurations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.chatapp.ChatAppV2.Jwt.JwtUtils;
import com.chatapp.ChatAppV2.Models.ChatMessage;
import com.chatapp.ChatAppV2.Models.MessageType;
import com.chatapp.ChatAppV2.Services.ChatService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ChatService chatService;

    final private ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap();

    @Autowired
    private JwtUtils jwt;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        sessions.put(username, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        sessions.remove(username);
    }

   

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {


        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message.getPayload());
        String content = node.get("content").asText();
        String receiver = node.has("receiver") ? node.get("receiver").asText() : null;

        final ChatMessage chatMessage = new ChatMessage(username, content, MessageType.CHAT, receiver);

        chatService.saveMessage(username, chatMessage, receiver);

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

}
