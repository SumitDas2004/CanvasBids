package com.canvasbids.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    String secret;

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> function){
        Claims claims = parseAllClaims(token);
        return function.apply(claims);
    }

    private Claims parseAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(generateSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(String username){
        return Jwts.builder()
                .signWith(generateSignKey())
                .expiration(new Date(System.currentTimeMillis()+1000*3600))
                .issuedAt(new Date(System.currentTimeMillis()))
                .subject(username)
                .compact();
    }

    private SecretKey generateSignKey() {
        byte[] decodedSecret = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decodedSecret);
    }

    public Date extractExpiry(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isExpired(String token) {
        return extractExpiry(token).before(new Date(System.currentTimeMillis()));
    }
}
