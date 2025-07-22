package com.chatapp.ChatAppV2.Configurations;

import java.util.List;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class UserHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        String query = request.getURI().getQuery(); //http://localhost:8080/user?username=boka

        // System.out.println("==== WebSocket Handshake Debug ====");
        // System.out.println("Full URL: " + request.getURI());
        // System.out.println("Raw Query: " + request.getURI().getQuery());

        if (query != null) {
            String username = query.substring(9);
            attributes.put("username", username);
            // System.out.println(username);
        }

        // List<String> username = request.getHeaders().get("username");
        // attributes.put("username", username);

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {

        // to do later

    }

}
