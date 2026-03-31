package com.sakthi.dev.Rental_Project.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {


    private final String SECRET = "your-256-bit-secret-key-which-should-be-very-long!"; // 32+ characters
private final long EXPIRATION_TIME = 1000 * 60 * 24;

private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

public String generateToken(String username,Long userId) {
    return Jwts.builder()
            .setSubject(username)
            .claim("userId",userId)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact();
}
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }
    public Long extractUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean validateToken(String token)
    {
        try{
            extractUsername(token);
            return true;
        }catch (JwtException exception){
            return false;
        }

    }
}
