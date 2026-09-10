package com.ping.simpledirectoryapi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Applies rate limiting to requests hitting the API before they reach a controller.
 */
@Component
public class ThrottleInterceptor implements HandlerInterceptor {

    public RateLimiter rateLimiter;

    public ThrottleInterceptor(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        try {
            rateLimiter.throttle();
            return true;
        } catch (RateLimitReachedException e) {
            response.setStatus(429);
            response.getWriter().write("rate limit exceeded");
            return false;
        }
    }
}
