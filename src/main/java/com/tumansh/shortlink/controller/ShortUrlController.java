package com.tumansh.shortlink.controller;

import com.tumansh.shortlink.dto.request.CreateShortUrlRequest;
import com.tumansh.shortlink.dto.response.*;
import com.tumansh.shortlink.service.ShortUrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/urls")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {

        this.shortUrlService = shortUrlService;
    }


    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request) {

        ShortUrlResponse response =
                shortUrlService.createShortUrl(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/redirect/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode, HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        String originalUrl = shortUrlService.getOriginalUrl(shortCode, ipAddress, userAgent);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    //    @GetMapping
    //    public List<UrlDetailsResponse> getAllUrls() {
    //        return shortUrlService.getAllUrls();
    //    }
    @GetMapping("/my")
    public ResponseEntity<List<MyUrlsResponse>> getMyUrls() {

        return ResponseEntity.ok(
                shortUrlService.getMyUrls()
        );
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<UrlDetailsResponse> getUrl(
            @PathVariable String shortCode) {

        return ResponseEntity.ok(
                shortUrlService.getUrl(shortCode)
        );
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<ApiResponse> deleteUrl(@PathVariable String shortCode) {
        shortUrlService.deleteUrl(shortCode);
        return ResponseEntity.ok(
                new ApiResponse(
                        "Short URL deleted successfully"
                )
        );
    }

    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<AnalyticsResponse> getAnalytics(
            @PathVariable String shortCode) {

        return ResponseEntity.ok(
                shortUrlService.getAnalytics(shortCode)
        );
    }
}