package com.pm.auth_service.utils;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtUtil {

    private final Key secretKey;

    public JwtUtil(@Value("${JWT_SECRET}") String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(
                StandardCharsets.UTF_8
        ));
        this.secretKey = io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(secretKey)
                .compact();



    }


    public void validateToken(String token) {

        try{
            Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);

        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature: " + e.getMessage());
        } catch(JwtException e){

            throw new JwtException(e.getMessage());
        }
    }
}
