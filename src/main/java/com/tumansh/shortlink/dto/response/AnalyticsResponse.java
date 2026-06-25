package com.tumansh.shortlink.dto.response;

public record AnalyticsResponse(

        String shortCode,
        Long totalClicks,
        Long uniqueVisitors

) {
}