package com.myprojecticaro.poc_jwt_secure_access.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/**
 * <p>
 * Utility class responsible for generating, parsing, and validating
 * JSON Web Tokens (JWT) used in authentication and authorization processes.
 * </p>
 *
 * <p>
 * This utility leverages the <b>io.jsonwebtoken</b> (JJWT) library to handle
 * the creation and verification of tokens signed with the HMAC SHA-256 algorithm.
 * Tokens contain the authenticated username, issuance date, and expiration date.
 * </p>
 *
 * <p><b>Token Structure:</b></p>
 * <ul>
 *     <li><b>Subject:</b> The username of the authenticated user.</li>
 *     <li><b>Issued At:</b> The date and time when the token was generated.</li>
 *     <li><b>Expiration:</b> 30 minutes after issuance by default.</li>
 * </ul>
 *
 * <p><b>Example Token:</b></p>
 * <pre>
 * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
 * eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5NjM2NjY0NywiZXhwIjoxNjk2MzY4NDQ3fQ.
 * pM79yRDLv2Xz1T0n1C3vR2C_yX1QmvVqUPJdT1JwDQk
 * </pre>
 *
 * <p><b>Important:</b></p>
 * <ul>
 *     <li>This example uses a hardcoded secret key for simplicity. In a production environment,
 *         use a strong, securely stored secret (e.g., environment variable or vault).</li>
 *     <li>Token expiration should be carefully chosen according to security requirements.</li>
 * </ul>
 *
 * @see io.jsonwebtoken.Jwts
 * @see io.jsonwebtoken.Claims
 * @see SignatureAlgorithm
 */
@Component
public class JwtUtil {

    /** Secret key used to sign and verify JWT tokens (for demonstration purposes only). */
    private static final String SECRET_KEY = "my-secret-key";

     /**
     * Generates a JWT token for the specified username.
     *
     * @param username The username for which the token is being generated.
     * @return A signed JWT token as a {@link String}.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

       /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token The JWT token.
     * @return The username contained in the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

     /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token The JWT token.
     * @return The expiration {@link Date}.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

     /**
     * Extracts a specific claim from the JWT token using the provided resolver function.
     *
     * @param token The JWT token.
     * @param claimsResolver A function that specifies which claim to extract.
     * @param <T> The type of the claim to be returned.
     * @return The extracted claim value.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}

