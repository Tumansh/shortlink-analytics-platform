package com.tumansh.shortlink.dto.response;

public record MyUrlsResponse(

        String shortCode,
        String originalUrl,
        Long clickCount

) {
}