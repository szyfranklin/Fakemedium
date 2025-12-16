package com.socialmedia.fakemedium.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key = Keys.hmacShaKeyFor(
            "change-me-to-a-very-long-secret-key-at-least-32-bytes!".getBytes(StandardCharsets.UTF_8)
    );

    public String generateToken(String subjectEmail) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(subjectEmail)
                .issuedAt(new Date(now))
                .expiration(new Date(now + 1000L * 60 * 60 * 24)) // 24h
                .signWith(key)
                .compact();
    }
}