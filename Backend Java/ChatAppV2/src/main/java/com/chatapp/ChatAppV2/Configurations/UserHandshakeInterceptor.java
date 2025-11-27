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
                if (request instanceof ServletServerHttpRequest s){
                    HttpServletRequest servletRequest = s.getServletRequest();
                    Cookie[] cookies = servletRequest.getCookies();
                    if (cookies != null){
                        for (Cookie cookie : cookies){
                            if (cookie.getName().equals("token")){
                                String token = cookie.getValue();
                                String username = jwt.extractUsername(token);
                                attributes.put("username", username);
                            }
                        }
                    }
                }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {

        // to do later

    }

}
