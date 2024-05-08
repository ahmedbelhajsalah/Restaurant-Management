package com.restaurant.restaurant_spring.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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

    private static Key getSignKey() {
        byte[] KeyBytes = Decoders.BASE64URL.decode(SECRET);
        return Keys.hmacShaKeyFor(KeyBytes);
    }

    public static String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public static boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private static boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private static Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private static Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).
                getBody();
    }
}
