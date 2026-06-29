package com.tumansh.shortlink.controller;

import com.tumansh.shortlink.dto.request.LoginRequest;
import com.tumansh.shortlink.dto.request.RegisterRequest;
import com.tumansh.shortlink.dto.response.ApiResponse;
import com.tumansh.shortlink.dto.response.AuthResponse;
import com.tumansh.shortlink.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        "User registered successfully"
                ));
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {
        return authService.login(request);
    }


}