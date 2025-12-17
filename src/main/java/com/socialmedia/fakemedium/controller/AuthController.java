package com.socialmedia.fakemedium.controller;

import com.socialmedia.fakemedium.dto.LoginRequest;
import com.socialmedia.fakemedium.dto.RegisterRequest;
import com.socialmedia.fakemedium.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req.email, req.password);
        return ResponseEntity.ok().body("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        String token = authService.login(req.email, req.password);
        return ResponseEntity.ok(token);
    }
}