package com.starchenko.hotelmanagementapi.controller;

import com.starchenko.hotelmanagementapi.dto.AuthRequest;
import com.starchenko.hotelmanagementapi.dto.AuthResponse;
import com.starchenko.hotelmanagementapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }
}