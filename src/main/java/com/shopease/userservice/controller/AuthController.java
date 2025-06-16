package com.shopease.userservice.controller;

import com.shopease.userservice.dto.JwtResponse;
import com.shopease.userservice.dto.LoginRequest;
import com.shopease.userservice.dto.RegisterRequest;
import com.shopease.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

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
}
