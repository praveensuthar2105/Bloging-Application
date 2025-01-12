package com.codewithpraveen.blog_app_apis.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.Serializable;
import java.util.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenHelper implements Serializable {

    private static SecretKey secret = new SecretKeySpec(Base64.getDecoder().decode("jwtTokenKey"), "HmacSHA256");

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public static String getUsernameFromToken(String token) {
      Claims claims =  getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        final Claims clains = getAllClaimsFromToken(token);
        return claimsResolver.apply(clains);
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public String generateToken(String username) {

        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);

    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .and()
                .signWith(secret)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String username1 = getUsernameFromToken(token);
        return (username1.equals(username) && !isTokenExpired(token));
    }
}
