package com.adamhorse.basicecommerce.authentication;

import com.adamhorse.basicecommerce.user.User;
import com.adamhorse.basicecommerce.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public User getCurrentUser() {
        // Gets the current authorization (tokens and stuff) of the person sending requests
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Extracts the email from the principle header
        Long userId = (Long) authentication.getPrincipal();

        return userRepository.findById(userId).orElse(null);
    }

    public User authenticateUser(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        return userRepository.findByEmail(email).orElseThrow();
    }
}
