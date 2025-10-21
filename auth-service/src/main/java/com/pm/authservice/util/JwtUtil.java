package com.pm.authservice.util;


import com.pm.authservice.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;

    public JwtUtil(@Value("${JWT_SECRET}") String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(
                StandardCharsets.UTF_8
        ));
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(String email, String role){
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 10))
                .signWith(key)
                .compact();
    }
}
