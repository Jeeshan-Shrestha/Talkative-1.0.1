package com.chatapp.ChatAppV2.Jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtFilter {

    public String generateKey() {
        return "hello";
    }

}
