package com.ping.simpledirectoryapi;

public class RateLimitReachedException extends RuntimeException {

    public RateLimitReachedException(String message) {
        super(message);
    }
}
