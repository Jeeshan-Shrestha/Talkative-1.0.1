package com.chatapp.ChatAppV2.Configurations;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.chatapp.ChatAppV2.Jwt.JwtUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class UserHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtUtils jwt;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
                if (request instanceof ServletServerHttpRequest sRequest){
                    HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
                    Cookie[] cookies = servletRequest.getCookies();
                    if (cookies != null){
                        for (Cookie cookie : cookies){
                            if (cookie.getName().equals("token")){
                                String token = cookie.getValue();
                                String username = jwt.extractUsername(token);

                                UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(username,null);
                                SecurityContextHolder.getContext().setAuthentication(auth);
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
