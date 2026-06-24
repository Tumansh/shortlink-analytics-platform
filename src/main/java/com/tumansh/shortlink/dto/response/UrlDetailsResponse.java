package com.tumansh.shortlink.dto.response;

public record UrlDetailsResponse(

        Long id,
        String originalUrl,
        String shortCode,
        Long clickCount

) {
}