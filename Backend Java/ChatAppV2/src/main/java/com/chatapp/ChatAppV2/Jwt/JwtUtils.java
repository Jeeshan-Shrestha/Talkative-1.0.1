package com.chatapp.ChatAppV2.Jwt;

import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.chatapp.ChatAppV2.Services.MyUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    final private String secretKey = "c3b9f1c6a8d2741ff3b0951826932aaf6a8d953cd5cf8b69b6c3b2073ef4fd2c";
    final private SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

    public String generateToken(MyUserDetails userDetails){

        HashMap<String,Object> claims = new HashMap<>();
        claims.put("username",userDetails.getUsername());
        claims.put("email",userDetails.getEmail());
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24 * 365))
                .claims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public String extractUsername(String token) {
        return (String)extractAllClaims(token).get("username");
    }

    public String extractEmail(String token){
        return (String)extractAllClaims(token).get("email");
    }

    public boolean validateToken(String username, UserDetails userDetails, String token){
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
