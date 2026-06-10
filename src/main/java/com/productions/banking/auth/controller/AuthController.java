package com.productions.banking.auth.controller;

import com.productions.banking.auth.dto.*;
import com.productions.banking.auth.entity.RefreshToken;
import com.productions.banking.auth.service.AuthService;
import com.productions.banking.auth.service.RefreshTokenService;
import com.productions.banking.common.response.MessageResponse;
import com.productions.banking.security.jwt.JwtService;
import com.productions.banking.user.entity.User;
import com.productions.banking.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok(
                new MessageResponse("User registered successfully")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.verify(request.getRefreshToken());

        User user = userRepository.findByUsername(refreshToken.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(
                new AuthResponse(newAccessToken, request.getRefreshToken())
        );
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> me(
            Authentication authentication) {

        UserInfoResponse response = authService.getCurrentUser(authentication.getName());

        return ResponseEntity.ok(response);
    }

}
