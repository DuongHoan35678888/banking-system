package com.productions.banking.auth.service;

import com.productions.banking.auth.dto.AuthResponse;
import com.productions.banking.auth.dto.LoginRequest;
import com.productions.banking.auth.dto.RegisterRequest;
import com.productions.banking.auth.dto.UserInfoResponse;
import com.productions.banking.common.exception.BadRequestException;
import com.productions.banking.common.exception.ResourceNotFoundException;
import com.productions.banking.role.entity.Role;
import com.productions.banking.role.entity.RoleName;
import com.productions.banking.role.repository.RoleRepository;
import com.productions.banking.security.jwt.JwtService;
import com.productions.banking.user.entity.User;
import com.productions.banking.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        Role customerRole = roleRepository
                .findByName(RoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new ResourceNotFoundException("Role ROLE_CUSTOMER not found"));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user.getRoles().add(customerRole);

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user.getUsername()).getToken();

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public UserInfoResponse getCurrentUser(String username) {

        User user = userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        return new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getRoles()
                        .stream()
                        .map(role -> role.getName().name())
                        .toList()
        );
    }
}