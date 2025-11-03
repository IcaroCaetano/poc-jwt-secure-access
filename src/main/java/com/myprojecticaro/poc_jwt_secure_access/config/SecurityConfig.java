package com.myprojecticaro.poc_jwt_secure_access.config;

import com.myprojecticaro.poc_jwt_secure_access.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>
 * Main Spring Security configuration class responsible for defining authentication,
 * authorization, and security filter chain rules.
 * </p>
 *
 * <p>
 * This configuration ensures that the application uses stateless JWT-based authentication
 * and does not rely on traditional session management. It registers the
 * {@link JwtAuthenticationFilter} to intercept and validate JWT tokens for all secured endpoints.
 * </p>
 *
 * <p><b>Key Responsibilities:</b></p>
 * <ul>
 *     <li>Disable CSRF protection since JWT is used for stateless authentication.</li>
 *     <li>Allow unauthenticated access to <code>/auth/**</code> endpoints (for login/token generation).</li>
 *     <li>Require authentication for all other requests.</li>
 *     <li>Set the session management policy to {@link SessionCreationPolicy#STATELESS}.</li>
 *     <li>Add the custom {@link JwtAuthenticationFilter} before Spring’s default {@link UsernamePasswordAuthenticationFilter}.</li>
 * </ul>
 *
 * <p><b>Example Behavior:</b></p>
 * <ul>
 *     <li><code>POST /auth/login</code> → Public (used to obtain JWT)</li>
 *     <li><code>GET /api/secure-data</code> → Requires a valid JWT in the Authorization header</li>
 * </ul>
 *
 * @see JwtAuthenticationFilter
 * @see UserService
 * @see AuthenticationManager
 * @see HttpSecurity
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserService userService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserService userService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
    }

      /**
     * Constructs a new {@code SecurityConfig} instance with the required dependencies.
     *
     * @param jwtAuthFilter The JWT authentication filter that validates tokens.
     * @param userService   The service used to load user details for authentication.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

     /**
     * Configures the Spring Security filter chain.
     * Defines which endpoints are secured, session policies, and filter order.
     *
     * @param http The {@link HttpSecurity} instance used to configure security behavior.
     * @return A configured {@link SecurityFilterChain} instance.
     * @throws Exception If any configuration error occurs.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

