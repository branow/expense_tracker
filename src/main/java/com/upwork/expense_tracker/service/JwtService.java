package com.upwork.expense_tracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

/**
 * {@code JwtService} is a service class to manage JWT: generate and parse tokens.
 * */
@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Value("${security.jwt.expiration-time.seconds}")
    private Integer expirationTime;


    public String getSubject(String jwt) {
        return jwtDecoder.decode(jwt).getSubject();
    }

    public String generateJwt(UserDetails user) {
        Instant now = Instant.now();
        Instant expired = LocalDateTime.now().plusSeconds(expirationTime)
                .atZone(ZoneId.systemDefault()).toInstant();

        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expired)
                .subject(user.getUsername())
                .claim("roles", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String getJwtFromBarerToken(String token) {
        return token.replaceFirst("bearer ", "");
    }

}
