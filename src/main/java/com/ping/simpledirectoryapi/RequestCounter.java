package com.ping.simpledirectoryapi;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Thin wrapper around Redis for counting requests. Redis is a key-value store
 * that all instances of the application share, so a counter incremented here
 * is visible to every instance. INCR is atomic, so two instances incrementing
 * the same key at the same time always see two separate counts.
 */
@Component
public class RequestCounter {

    private StringRedisTemplate redisTemplate;

    public RequestCounter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /** Adds 1 to the value stored at key and returns the new count. The count starts at 1 the first time a key is seen. */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /** Deletes the key automatically once its count has gone this many seconds without changing. */
    public void expire(String key, int seconds) {
        redisTemplate.expire(key, Duration.ofSeconds(seconds));
    }
}
