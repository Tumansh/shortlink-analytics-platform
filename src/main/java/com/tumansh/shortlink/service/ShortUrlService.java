package com.tumansh.shortlink.service;

import com.tumansh.shortlink.dto.request.CreateShortUrlRequest;
import com.tumansh.shortlink.dto.response.AnalyticsResponse;
import com.tumansh.shortlink.dto.response.MyUrlsResponse;
import com.tumansh.shortlink.dto.response.ShortUrlResponse;
import com.tumansh.shortlink.dto.response.UrlDetailsResponse;
import com.tumansh.shortlink.entity.Analytics;
import com.tumansh.shortlink.entity.ShortUrl;
import com.tumansh.shortlink.entity.User;
import com.tumansh.shortlink.exception.AccessDeniedException;
import com.tumansh.shortlink.exception.ShortUrlNotFoundException;
import com.tumansh.shortlink.exception.UserNotFoundException;
import com.tumansh.shortlink.repo.AnalyticsRepo;
import com.tumansh.shortlink.repo.ShortUrlRepo;
import com.tumansh.shortlink.repo.UserRepo;
import com.tumansh.shortlink.util.ShortCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ShortUrlService {
    private static final Logger logger =
            LoggerFactory.getLogger(ShortUrlService.class);
    @Value("${app.redis.cache-ttl-minutes}")
    private long cacheTtlMinutes;
    private final ShortUrlRepo shortUrlRepository;
    private final AnalyticsRepo analyticsRepo;
    private final UserRepo userRepo;
    private final RedisTemplate<String, String> redisTemplate;

    public ShortUrlService(
            ShortUrlRepo shortUrlRepository,
            AnalyticsRepo analyticsRepo,
            UserRepo userRepo,
            RedisTemplate<String, String> redisTemplate) {

        this.shortUrlRepository = shortUrlRepository;
        this.analyticsRepo = analyticsRepo;
        this.userRepo = userRepo;
        this.redisTemplate = redisTemplate;
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

        User user = getCurrentUser();
        shortUrl.setUser(user);
        shortUrlRepository.save(shortUrl);

        return new ShortUrlResponse(
                shortCode,
                "http://localhost:8080/urls/redirect/"
                        + shortCode
        );


    }
    //Helper method for redis logic
    private String getCacheKey(
            String shortCode) {

        return "url:" + shortCode;
    }
    private String getCachedOriginalUrl(
            String shortCode) {

        try {

            return redisTemplate
                    .opsForValue()
                    //.get(shortCode);
                    .get(getCacheKey(shortCode));

        } catch (RedisConnectionFailureException ex) {

//        System.out.println(
//                "Redis unavailable. Falling back to MySQL."
//        );

            logger.warn(
                    "Redis unavailable. Falling back to MySQL.",
                    ex
            );

            return null;
        }
    }
    private void cacheOriginalUrl(
            String shortCode,
            String originalUrl) {

        try {

            redisTemplate
                    .opsForValue()
                    .set(
                            //shortCode,
                            getCacheKey(shortCode),

                            originalUrl,

                            cacheTtlMinutes,

                            TimeUnit.MINUTES
                    );

        } catch (RedisConnectionFailureException ex) {

//        System.out.println(
//                "Redis unavailable. Cache not updated."
//        );

            logger.warn(
                    "Redis unavailable. Cache not updated.",
                    ex
            );
        }
    }
    private void saveAnalytics(
            ShortUrl shortUrl,
            String ipAddress,
            String userAgent) {

        Analytics analytics =
                new Analytics();

        analytics.setIpAddress(
                ipAddress
        );

        analytics.setUserAgent(
                userAgent
        );

        analytics.setClickedAt(
                LocalDateTime.now()
        );

        analytics.setShortUrl(
                shortUrl
        );

        analyticsRepo.save(
                analytics
        );
    }
    private void incrementClickCount(
            ShortUrl shortUrl) {

        shortUrl.setClickCount(
                shortUrl.getClickCount() + 1
        );

        shortUrlRepository.save(
                shortUrl
        );
    }
    public String getOriginalUrl(
            String shortCode,
            String ipAddress,
            String userAgent) {

        String originalUrl =
                getCachedOriginalUrl(shortCode);

        ShortUrl shortUrl;

        if (originalUrl != null) {

//        System.out.println("Cache Hit");

            logger.info(
                    "Cache hit for shortCode={}",
                    shortCode
            );

            /*
             * We intentionally fetch the entity even on cache hit
             * because analytics recording and click count updates
             * require the ShortUrl entity.
             */

            shortUrl =
                    findShortUrl(shortCode);

        } else {

//        System.out.println("Cache Miss");

            logger.info(
                    "Cache miss for shortCode={}",
                    shortCode
            );

            shortUrl =
                    findShortUrl(shortCode);

            originalUrl =
                    shortUrl.getOriginalUrl();

            cacheOriginalUrl(
                    shortCode,
                    originalUrl
            );
        }

        incrementClickCount(
                shortUrl
        );

        saveAnalytics(
                shortUrl,
                ipAddress,
                userAgent
        );

        return originalUrl;
    }

    //    public List<UrlDetailsResponse> getAllUrls() {
    //
    //        return shortUrlRepository.findAll()
    //                .stream()
    //                .map(url -> new UrlDetailsResponse(
    //                        url.getId(),
    //                        url.getOriginalUrl(),
    //                        url.getShortCode(),
    //                        url.getClickCount()
    //                ))
    //                .toList();
    //    }

    public UrlDetailsResponse getUrl(
            String shortCode) {

        ShortUrl shortUrl =
                findShortUrl(shortCode);

        validateOwnership(shortUrl);

        return new UrlDetailsResponse(
                shortUrl.getId(),
                shortUrl.getOriginalUrl(),
                shortUrl.getShortCode(),
                shortUrl.getClickCount()
        );
    }

    private void validateOwnership(
            ShortUrl shortUrl) {

        User currentUser =
                getCurrentUser();

        if (!shortUrl.getUser()
                .getId()
                .equals(currentUser.getId())) {

            throw new AccessDeniedException(
                    "You do not own this URL"
            );
        }
    }

    public void deleteUrl(
            String shortCode) {

        ShortUrl shortUrl =
                findShortUrl(shortCode);

        validateOwnership(shortUrl);

        shortUrlRepository.delete(shortUrl);

        try {

            redisTemplate.delete(
                    getCacheKey(shortCode)
            );

            logger.info(
                    "Cache evicted for shortCode={}",
                    shortCode
            );

        } catch (RedisConnectionFailureException ex) {

            logger.warn(
                    "Redis unavailable. Cache eviction skipped.",
                    ex
            );
        }
    }

    public AnalyticsResponse getAnalytics(
            String shortCode) {

        ShortUrl shortUrl =
                findShortUrl(shortCode);

        validateOwnership(shortUrl);

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

    private User getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        ));
    }

    public List<MyUrlsResponse> getMyUrls() {

        User user = getCurrentUser();

        return shortUrlRepository
                .findByUser(user)
                .stream()
                .map(url ->
                        new MyUrlsResponse(
                                url.getShortCode(),
                                url.getOriginalUrl(),
                                url.getClickCount()
                        )
                )
                .toList();
    }

    // Helper function
    private ShortUrl findShortUrl(
            String shortCode) {

        return shortUrlRepository
                .findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortUrlNotFoundException(
                                "Short URL not found"
                        ));
    }
}