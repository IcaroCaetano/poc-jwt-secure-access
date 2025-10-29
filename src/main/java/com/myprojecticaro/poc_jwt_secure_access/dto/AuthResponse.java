package com.myprojecticaro.poc_jwt_secure_access.dto;

/**
 * DTO returned to the client after successful authentication,
 * containing the generated JWT token.
 */
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

