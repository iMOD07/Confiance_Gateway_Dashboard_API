package com.confiance.gateway.service;

import com.confiance.gateway.entity.AdminUser;
import com.confiance.gateway.model.request.LoginRequest;
import com.confiance.gateway.model.response.TokenResponse;
import com.confiance.gateway.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse login(LoginRequest request) {
        AdminUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getEnabled()) {
            throw new RuntimeException("Account is deactivated");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());

        log.info("Admin logged in: {}", user.getUsername());

        return TokenResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationSeconds())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}