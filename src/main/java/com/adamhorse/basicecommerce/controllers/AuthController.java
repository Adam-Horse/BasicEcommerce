package com.adamhorse.basicecommerce.controllers;

import com.adamhorse.basicecommerce.dtos.JwtResponse;
import com.adamhorse.basicecommerce.dtos.LoginRequest;
import com.adamhorse.basicecommerce.dtos.UserDto;
import com.adamhorse.basicecommerce.entities.User;
import com.adamhorse.basicecommerce.mappers.UserMapper;
import com.adamhorse.basicecommerce.repositories.UserRepository;
import com.adamhorse.basicecommerce.services.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        String token = jwtService.generateToken(request.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/validate")
    public boolean validate(@RequestHeader("Authorization") String authHeader) {
        System.out.println("Validate called");
        String token = authHeader.replace("Bearer ", "");

        return jwtService.validateToken(token);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {

        // Gets the current authorization (tokens and stuff) of the person sending requests
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Extracts the email from the principle header
        String email = (String) authentication.getPrincipal();

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDto userDto = userMapper.toDto(user);

        return ResponseEntity.ok(userDto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
