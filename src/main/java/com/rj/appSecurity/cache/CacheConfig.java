package com.rj.appSecurity.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Value("${spring.cache.expiryDuration}")
    private int expiryDuration;

    @Bean()
    public CacheStore<String, Integer> userCache() {
        return new CacheStore<>(expiryDuration, TimeUnit.SECONDS);
    }

}
