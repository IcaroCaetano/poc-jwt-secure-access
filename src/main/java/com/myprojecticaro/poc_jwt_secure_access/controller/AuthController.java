package com.myprojecticaro.poc_jwt_secure_access.controller;

import com.myprojecticaro.poc_jwt_secure_access.dto.AuthRequest;
import com.myprojecticaro.poc_jwt_secure_access.dto.AuthResponse;
import com.myprojecticaro.poc_jwt_secure_access.service.JwtService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * REST controller responsible for handling authentication-related endpoints,
 * including user login and service health checks.
 * </p>
 *
 * <p>
 * This controller exposes a login endpoint that validates user credentials through
 * {@link JwtService} and returns a JWT token upon successful authentication.
 * It also provides a simple health check endpoint to verify that the authentication
 * service is running properly.
 * </p>
 *
 * <p><b>Endpoints:</b></p>
 * <ul>
 *   <li><code>POST /auth/login</code> — Authenticates a user and returns a JWT token.</li>
 *   <li><code>GET /auth/check</code> — Returns a simple confirmation message indicating the service is up.</li>
 * </ul>
 *
 * <p><b>Example Request:</b></p>
 * <pre>
 * POST /auth/login
 * {
 *   "username": "admin",
 *   "password": "1234"
 * }
 * </pre>
 *
 * <p><b>Example Response:</b></p>
 * <pre>
 * {
 *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
 * }
 * </pre>
 *
 * @see JwtService
 * @see AuthRequest
 * @see AuthResponse
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

     /**
     * Constructs a new {@code AuthController} instance.
     *
     * @param jwtService The service responsible for authenticating users and generating JWT tokens.
     */
    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

     /**
     * Authenticates a user based on the provided credentials and returns a JWT token if valid.
     *
     * @param request The authentication request containing the username and password.
     * @return An {@link AuthResponse} object containing the generated JWT token.
     */
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        String token = jwtService.authenticate(request.getUsername(), request.getPassword());
        return new AuthResponse(token);
    }

    /**
     * Simple health check endpoint to confirm that the authentication service is operational.
     *
     * @return A string message indicating that the service is running.
     */
    @GetMapping("/check")
    public String check() {
        return "Authentication service is up!";
    }
}
