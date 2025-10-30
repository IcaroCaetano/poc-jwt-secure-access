package com.myprojecticaro.poc_jwt_secure_access.controller;

import com.myprojecticaro.poc_jwt_secure_access.dto.AuthRequest;
import com.myprojecticaro.poc_jwt_secure_access.dto.AuthResponse;
import com.myprojecticaro.poc_jwt_secure_access.service.JwtService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling authentication endpoints.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        String token = jwtService.authenticate(request.getUsername(), request.getPassword());
        return new AuthResponse(token);
    }

    @GetMapping("/check")
    public String check() {
        return "Authentication service is up!";
    }
}
