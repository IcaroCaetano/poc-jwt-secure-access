package com.myprojecticaro.poc_jwt_secure_access.config;

import com.myprojecticaro.poc_jwt_secure_access.service.UserService;
import com.myprojecticaro.poc_jwt_secure_access.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * <p>
 * Filter responsible for intercepting all incoming HTTP requests and validating JWT tokens.
 * It ensures that only authenticated users with valid tokens can access secured endpoints.
 * </p>
 *
 * <p>
 * This filter extracts the JWT token from the "Authorization" header of each request,
 * validates the token using {@link JwtUtil}, and retrieves user details through {@link UserService}.
 * If the token is valid, it sets the authentication object in the {@link SecurityContextHolder},
 * allowing Spring Security to recognize the request as authenticated.
 * </p>
 *
 * <p><b>Typical Flow:</b></p>
 * <ol>
 *     <li>Intercept the request via {@link #doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}</li>
 *     <li>Check if the "Authorization" header exists and starts with "Bearer "</li>
 *     <li>Extract the JWT token and retrieve the username from it</li>
 *     <li>Validate the token and set the authentication in the Spring Security context</li>
 *     <li>Continue the request filter chain</li>
 * </ol>
 *
 * <p>
 * This filter is executed once per request because it extends {@link OncePerRequestFilter}.
 * </p>
 *
 * <p><b>Example Authorization Header:</b></p>
 * <pre>
 * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
 * </pre>
 *
 * @see JwtUtil
 * @see UserService
 * @see OncePerRequestFilter
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

     /**
     * Constructs a new {@code JwtAuthenticationFilter} with the required dependencies.
     *
     * @param jwtUtil     Utility class for generating and validating JWT tokens.
     * @param userService Service used to load user details based on the username extracted from the token.
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

       /**
     * Intercepts each incoming HTTP request to validate the JWT token.
     * If a valid token is found, sets the authenticated user in the security context.
     *
     * @param request     The incoming {@link HttpServletRequest}.
     * @param response    The outgoing {@link HttpServletResponse}.
     * @param filterChain The {@link FilterChain} to continue processing the request.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException      If an input/output error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        final String username = jwtUtil.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userService.loadUserByUsername(username);
            if (jwtUtil.isTokenValid(token, username)) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

