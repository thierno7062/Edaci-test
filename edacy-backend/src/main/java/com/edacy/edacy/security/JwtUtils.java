package com.edacy.edacy.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /* @Value("${edacy.jwtSecret}")
    private String jwtSecret; */
    SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    @Value("${edacy.jwtExpirationMs}")
    private int jwtExpirationMs;

/*     private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    } */

    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                // .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .signWith(jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("Erreur de validation JWT : {}", e.getMessage());
        }
        return false;
    }

    public long getJwtExpirationMs() {
        return jwtExpirationMs;
    }
}
