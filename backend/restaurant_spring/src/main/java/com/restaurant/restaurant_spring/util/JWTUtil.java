package com.restaurant.restaurant_spring.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    public static final String SECRET = "4902851400552686446144755289908079114260298616212454977512446152338588";

    public String generateToken(String email){
        Map<String, Object> claims = new HashMap<>();

        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 303))
                .signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] KeyBytes = Decoders.BASE64URL.decode(SECRET);
        return Keys.hmacShaKeyFor(KeyBytes);
    }
}
