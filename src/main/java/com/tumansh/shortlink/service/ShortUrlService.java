package com.tumansh.shortlink.service;

import com.tumansh.shortlink.dto.request.CreateShortUrlRequest;
import com.tumansh.shortlink.dto.response.ShortUrlResponse;
import com.tumansh.shortlink.entity.ShortUrl;
import com.tumansh.shortlink.exception.ShortUrlNotFoundException;
import com.tumansh.shortlink.repo.ShortUrlRepo;
import com.tumansh.shortlink.util.ShortCodeGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShortUrlService {

    private final ShortUrlRepo shortUrlRepository;

    public ShortUrlService(
            ShortUrlRepo shortUrlRepository) {

        this.shortUrlRepository = shortUrlRepository;
    }

    public ShortUrlResponse createShortUrl(
            CreateShortUrlRequest request) {

        String shortCode;

        do {

            shortCode =
                    ShortCodeGenerator.generate();

        } while (
                shortUrlRepository
                        .existsByShortCode(shortCode)
        );

        ShortUrl shortUrl = new ShortUrl();

        shortUrl.setOriginalUrl(
                request.originalUrl()
        );

        shortUrl.setShortCode(
                shortCode
        );

        shortUrl.setClickCount(0L);

        shortUrl.setCreatedAt(
                LocalDateTime.now()
        );

        shortUrlRepository.save(shortUrl);

        return new ShortUrlResponse(
                shortCode,
                "http://localhost:8080/r/"
                        + shortCode
        );


    }
    public String getOriginalUrl(
            String shortCode) {

        ShortUrl shortUrl =
                shortUrlRepository
                        .findByShortCode(shortCode)
                        .orElseThrow(() ->
                                new ShortUrlNotFoundException(
                                        "Short URL not found"
                                ));

        shortUrl.setClickCount(
                shortUrl.getClickCount() + 1
        );

        shortUrlRepository.save(shortUrl);

        return shortUrl.getOriginalUrl();
    }
}