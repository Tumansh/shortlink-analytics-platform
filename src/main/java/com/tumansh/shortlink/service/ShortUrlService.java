package com.tumansh.shortlink.service;

import com.tumansh.shortlink.dto.request.CreateShortUrlRequest;
import com.tumansh.shortlink.dto.response.AnalyticsResponse;
import com.tumansh.shortlink.dto.response.ShortUrlResponse;
import com.tumansh.shortlink.dto.response.UrlDetailsResponse;
import com.tumansh.shortlink.entity.Analytics;
import com.tumansh.shortlink.entity.ShortUrl;
import com.tumansh.shortlink.exception.ShortUrlNotFoundException;
import com.tumansh.shortlink.repo.AnalyticsRepo;
import com.tumansh.shortlink.repo.ShortUrlRepo;
import com.tumansh.shortlink.util.ShortCodeGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShortUrlService {

    private final ShortUrlRepo shortUrlRepository;
    private final AnalyticsRepo analyticsRepo;

    public ShortUrlService(
            ShortUrlRepo shortUrlRepository, AnalyticsRepo analyticsRepo) {

        this.shortUrlRepository = shortUrlRepository;
        this.analyticsRepo = analyticsRepo;
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
                "http://localhost:8080/redirect/"
                        + shortCode
        );




    }
    public String getOriginalUrl(
            String shortCode,
            String ipAddress,
            String userAgent) {

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

        Analytics analytics =
                new Analytics();

        analytics.setIpAddress(ipAddress);

        analytics.setUserAgent(userAgent);

        analytics.setClickedAt(
                LocalDateTime.now()
        );

        analytics.setShortUrl(shortUrl);

        analyticsRepo.save(analytics);

        return shortUrl.getOriginalUrl();
    }

    public List<UrlDetailsResponse> getAllUrls() {

        return shortUrlRepository.findAll()
                .stream()
                .map(url -> new UrlDetailsResponse(
                        url.getId(),
                        url.getOriginalUrl(),
                        url.getShortCode(),
                        url.getClickCount()
                ))
                .toList();
    }

    public UrlDetailsResponse getUrl(
            String shortCode) {

        ShortUrl shortUrl =
                shortUrlRepository
                        .findByShortCode(shortCode)
                        .orElseThrow(() ->
                                new ShortUrlNotFoundException(
                                        "Short URL not found"
                                ));

        return new UrlDetailsResponse(
                shortUrl.getId(),
                shortUrl.getOriginalUrl(),
                shortUrl.getShortCode(),
                shortUrl.getClickCount()
        );
    }

    public void deleteUrl(
            String shortCode) {

        ShortUrl shortUrl =
                shortUrlRepository
                        .findByShortCode(shortCode)
                        .orElseThrow(() ->
                                new ShortUrlNotFoundException(
                                        "Short URL not found"
                                ));

        shortUrlRepository.delete(shortUrl);
    }

    public AnalyticsResponse getAnalytics(
            String shortCode) {

        ShortUrl shortUrl =
                shortUrlRepository
                        .findByShortCode(shortCode)
                        .orElseThrow(() ->
                                new ShortUrlNotFoundException(
                                        "Short URL not found"
                                ));

        long totalClicks =
                analyticsRepo.countByShortUrl(
                        shortUrl
                );

        long uniqueVisitors =
                analyticsRepo.countUniqueVisitors(
                        shortUrl
                );

        return new AnalyticsResponse(
                shortCode,
                totalClicks,
                uniqueVisitors
        );
    }
}