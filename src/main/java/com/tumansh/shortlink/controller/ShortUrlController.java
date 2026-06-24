package com.tumansh.shortlink.controller;

import com.tumansh.shortlink.dto.request.CreateShortUrlRequest;
import com.tumansh.shortlink.dto.response.ShortUrlResponse;
import com.tumansh.shortlink.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/urls")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(
            ShortUrlService shortUrlService) {

        this.shortUrlService =
                shortUrlService;
    }

    @PostMapping
    public ShortUrlResponse createShortUrl(

            @Valid
            @RequestBody
            CreateShortUrlRequest request) {

        return shortUrlService
                .createShortUrl(request);
    }
    @GetMapping("/r/{shortCode}")
    public ResponseEntity<Void> redirect(

            @PathVariable
            String shortCode) {

        String originalUrl =
                shortUrlService
                        .getOriginalUrl(shortCode);

        HttpHeaders headers =
                new HttpHeaders();

        headers.setLocation(
                URI.create(originalUrl)
        );

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }

}