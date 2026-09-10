package com.ping.simpledirectoryapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RateLimiter {

    private static Logger LOGGER = LoggerFactory.getLogger(RateLimiter.class);

    private RequestCounter requestCounter;

    private int limit;

    private int windowSeconds;

    public RateLimiter(RequestCounter requestCounter,
                       @Value("${app.directoryRateLimiter.defaultLimitWithinWindow:5}") int limit,
                       @Value("${app.directoryRateLimiter.accumulationWindowSeconds:60}") int windowSeconds) {
        this.requestCounter = requestCounter;
        this.limit = limit;
        this.windowSeconds = windowSeconds;
    }

    public void throttle() {
        String redisKey = "directory";
        try {
            Long count = requestCounter.increment(redisKey);
            if (count != null && count == 1) {
                requestCounter.expire(redisKey, windowSeconds);
            }
            if (count != null && count > limit) {
                LOGGER.info("rate limit reached for {}", redisKey);
                throw new RateLimitReachedException("too many requests, try again later");
            }
        } catch (RateLimitReachedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.info("rate limiter not available, allowing request {}", e.getMessage());
        }
    }
}
