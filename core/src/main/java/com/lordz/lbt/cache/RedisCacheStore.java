package com.lordz.lbt.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zzz
 * @date ：Created in 7/17/19 9:40 AM
 */
@Component
public class RedisCacheStore implements CacheStore<String, String> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Optional<String> get(String key) {
        return Optional.of(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void put(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }

    @Override
    public Boolean putIfAbsent(String key, String value, long timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key,value,timeout,timeUnit);
    }

    @Override
    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
