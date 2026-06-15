package com.confiance.gateway.controller;

import com.confiance.gateway.model.request.LoginRequest;
import com.confiance.gateway.model.response.ApiResponse;
import com.confiance.gateway.model.response.TokenResponse;
import com.confiance.gateway.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            TokenResponse token = authService.login(request);
            return ResponseEntity.ok(ApiResponse.success("Login successful", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error(401, e.getMessage()));
        }
    }
}
