package com.upwork.expense_tracker.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * It's better to use {@link JwtService}
 * */
@Deprecated
@Component
public class TokenUtility {

    private Set<String> revokedTokens = new HashSet<>();

    private static final String SECRET = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";

    private static final long EXPIRATION_TIME = 900_000; // 15 minutes

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            return null;
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            token = token.substring(7);
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean validateToken(String token) {

        if (revokedTokens.contains(token)) {
            return false;
        }
        return !isTokenExpired(token);
    }

    public void revokeToken(String token) {
        revokedTokens.add(token);
    }

    public String refreshAccessToken(String refreshToken) {

        String username = extractUsername(refreshToken);
        if (username != null) {
            return generateToken(username);
        }
        return null;
    }
}