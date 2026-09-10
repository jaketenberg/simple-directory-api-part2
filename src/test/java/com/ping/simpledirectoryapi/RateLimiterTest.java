package com.ping.simpledirectoryapi;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class RateLimiterTest {

    @Test
    void testThrottleAllowsRequestBelowLimit() {
        RequestCounter requestCounter = mock(RequestCounter.class);
        when(requestCounter.increment(anyString())).thenReturn(2L);

        RateLimiter limiter = new RateLimiter(requestCounter, 5, 60);

        assertDoesNotThrow(() -> limiter.throttle());
    }

    @Test
    void testThrottleThrowsWhenLimitReached() {
        RequestCounter requestCounter = mock(RequestCounter.class);
        when(requestCounter.increment(anyString())).thenReturn(6L);

        RateLimiter limiter = new RateLimiter(requestCounter, 5, 60);

        assertThrows(RateLimitReachedException.class, () -> limiter.throttle());
    }

    @Test
    void testThrottleAllowsRequestWhenCounterUnavailable() {
        RequestCounter requestCounter = mock(RequestCounter.class);
        when(requestCounter.increment(anyString())).thenThrow(new RuntimeException("connection refused"));

        RateLimiter limiter = new RateLimiter(requestCounter, 5, 60);

        assertDoesNotThrow(() -> limiter.throttle());
    }
}
