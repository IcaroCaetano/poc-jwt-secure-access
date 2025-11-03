package com.myprojecticaro.poc_jwt_secure_access.service;

import com.myprojecticaro.poc_jwt_secure_access.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Service responsible for authenticating users and generating JSON Web Tokens (JWT).
 * <p>
 * This service validates user credentials using Spring Security's {@link AuthenticationManager}.
 * Upon successful authentication, it generates a signed JWT token via {@link JwtUtil}.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * String token = jwtService.authenticate("admin", "1234");
 * }</pre>
 *
 * <p>
 * The generated token can be used for stateless authentication in subsequent requests.
 * </p>
 *
 * @author Icaro
 * @version 1.0
 * @see AuthenticationManager
 * @see JwtUtil
 */
@Service
public class JwtService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

      /**
     * Constructs a new {@code JwtService} with the given authentication manager and JWT utility.
     *
     * @param authenticationManager the {@link AuthenticationManager} used to authenticate users
     * @param jwtUtil the {@link JwtUtil} utility used to generate and validate JWT tokens
     */
    public JwtService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Authenticates a user with the provided username and password.
     * <p>
     * If authentication succeeds, a signed JWT token is generated and returned.
     * Otherwise, a {@link RuntimeException} is thrown.
     * </p>
     *
     * @param username the username of the user attempting to authenticate
     * @param password the raw password of the user
     * @return a signed JWT token representing the authenticated user
     * @throws RuntimeException if the authentication fails due to invalid credentials
     */
    public String authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(username);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
