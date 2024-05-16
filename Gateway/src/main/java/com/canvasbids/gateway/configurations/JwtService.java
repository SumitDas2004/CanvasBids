package com.canvasbids.gateway.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
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

    private SecretKey generateSignKey() {
        byte[] decodedSecret = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decodedSecret);
    }
}
