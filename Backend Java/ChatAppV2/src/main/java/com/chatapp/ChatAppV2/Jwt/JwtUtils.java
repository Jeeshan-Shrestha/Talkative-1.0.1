package com.chatapp.ChatAppV2.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private String secret = "sdlkjadsjfsidflkjsda;lkfj;askldjf;ajsd;fkja;sldkfj;laskdjf;l";

    public String generateKey(String username) {
        HashMap<String, Object> claims = new HashMap<>();
        String subject = username;
        return generateToken(claims, subject);
    }

    public String generateToken(HashMap<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getSignKey())
                .compact();
    }

    public Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

}
