package com.tumansh.shortlink.repo;

import com.tumansh.shortlink.entity.Analytics;
import com.tumansh.shortlink.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnalyticsRepo
        extends JpaRepository<Analytics, Long> {
    long countByShortUrl(ShortUrl shortUrl);

    @Query("""
             SELECT COUNT(DISTINCT a.ipAddress)
             FROM Analytics a
             WHERE a.shortUrl = :shortUrl
            """)
    long countUniqueVisitors(
            @Param("shortUrl")
            ShortUrl shortUrl
    );
}