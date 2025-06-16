package com.shopease.userservice.controller;

import com.shopease.userservice.dto.RegisterRequest;
import com.shopease.userservice.model.User;
import com.shopease.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
