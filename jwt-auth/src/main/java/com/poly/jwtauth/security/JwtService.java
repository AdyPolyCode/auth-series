package com.poly.jwtauth.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String create(String username, Map<String, ?> claims) {
        return Jwts.builder()
                .signWith(getKey())
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong("1377341983")))
                .issuedAt(new Date(System.currentTimeMillis()))
                .subject(username)
                .claims(claims)
                .compact();
    }

    public boolean isExpired(String token) {
        JwtParser parser = Jwts.parser().verifyWith(getKey()).build();
        Date isValid = parser.parseSignedClaims(token).getPayload().getExpiration();
        return new Date().before(isValid);
    }

    public String getUserName(String token) {
        JwtParser parser = Jwts.parser().verifyWith(getKey()).build();
        return parser.parseSignedClaims(token).getPayload().getSubject();
    }

    private SecretKey getKey() {
        byte[] bytes = secret.getBytes();
        return Keys.hmacShaKeyFor(bytes);
    }
}
