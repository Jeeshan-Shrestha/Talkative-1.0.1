package com.chatapp.ChatAppV2.Configurations;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.chatapp.ChatAppV2.Jwt.JwtUtils;
import com.chatapp.ChatAppV2.Services.MyUserDetailsService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtUtils jwt;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

   @Override
public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
        Map<String, Object> attributes) throws Exception {

    if (!(request instanceof ServletServerHttpRequest s)) {
        response.setStatusCode(org.springframework.http.HttpStatus.BAD_REQUEST);
        return false;
    }

    HttpServletRequest servletRequest = s.getServletRequest();
    String token = null;

    Cookie[] cookies = servletRequest.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
                break;
            }
        }
    }

    if (token == null) {
        token = servletRequest.getParameter("token");
    }

    if (token == null) {
        response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
        return false;
    }

    String username = jwt.extractUsername(token);
    if (username == null) {
        response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
        return false;
    }

    attributes.put("username", username);
    return true;
} 

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {
        // to do later
    }
}