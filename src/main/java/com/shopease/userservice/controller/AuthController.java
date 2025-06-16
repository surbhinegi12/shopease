package com.shopease.userservice.controller;

import com.shopease.userservice.dto.JwtResponse;
import com.shopease.userservice.dto.LoginRequest;
import com.shopease.userservice.dto.RefreshTokenRequest;
import com.shopease.userservice.dto.RegisterRequest;
import com.shopease.userservice.model.RefreshToken;
import com.shopease.userservice.service.RefreshTokenService;
import com.shopease.userservice.service.UserService;
import com.shopease.userservice.util.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String message = userService.registerUser(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        JwtResponse jwtResponse = userService.authenticateUser(request);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        refreshTokenService.verifyExpiration(refreshToken);

        String newToken = jwtUtil.generateToken(refreshToken.getUser().getUsername(), refreshToken.getUser().getRole());

        return ResponseEntity.ok(new JwtResponse(newToken, requestRefreshToken));
    }

}
