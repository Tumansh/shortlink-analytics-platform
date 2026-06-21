package com.tumansh.shortlink.controller;

import com.tumansh.shortlink.security.JWTService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private final JWTService jwtService;

    public TestController(
            JWTService jwtService) {

        this.jwtService = jwtService;
    }

    @GetMapping("/email")
    public String getEmail(
            @RequestParam String token) {

        return jwtService.extractEmail(token);
    }
    @GetMapping("/validate")
    public Boolean validate(
            @RequestParam String token){

        return jwtService.isTokenValid(token);
    }
}