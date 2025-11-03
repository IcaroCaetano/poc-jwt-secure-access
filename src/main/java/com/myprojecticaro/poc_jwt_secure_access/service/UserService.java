package com.myprojecticaro.poc_jwt_secure_access.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Service responsible for loading user details required for authentication.
 * <p>
 * This implementation of {@link UserDetailsService} provides an in-memory
 * user for authentication purposes within this Proof of Concept (POC).
 * </p>
 *
 * <p>
 * The default user is:
 * <ul>
 *     <li><b>Username:</b> admin</li>
 *     <li><b>Password:</b> 1234</li>
 *     <li><b>Role:</b> ADMIN</li>
 * </ul>
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * UserDetails user = userService.loadUserByUsername("admin");
 * }</pre>
 *
 * <p>
 * In a real-world application, this service would typically query a database or an external identity provider
 * instead of using hardcoded credentials.
 * </p>
 *
 * @author Icaro
 * @version 1.0
 * @see UserDetailsService
 * @see User
 * @see UsernameNotFoundException
 */
@Service
public class UserService implements UserDetailsService {

      /**
     * Loads a user by their username.
     * <p>
     * If the username is "admin", a static {@link UserDetails} object is returned with predefined credentials.
     * Otherwise, a {@link UsernameNotFoundException} is thrown.
     * </p>
     *
     * @param username the username identifying the user whose data is required
     * @return a {@link UserDetails} object containing the user's information
     * @throws UsernameNotFoundException if the user cannot be found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            return User.withUsername("admin")
                    .password("{noop}1234") // {noop} means no password encoder
                    .roles("ADMIN")
                    .build();
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
