package com.edacy.edacy.dto;

import java.time.Instant;
import lombok.Data;

@Data
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Instant expiresAt;

    
    public JwtResponse(String token, long expirationMs) {
        this.token = token;
        this.expiresAt = Instant.now().plusMillis(expirationMs);
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

}
