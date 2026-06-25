package com.tumansh.shortlink.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}