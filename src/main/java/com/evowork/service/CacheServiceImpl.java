package com.evowork.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.evowork.config.ApplicationConfig;
import com.evowork.exception.IllegalCastException;

/**
 * @author tuannd
 * @date 18/07/2016
 * @since 1.0
 */
@Service
public class CacheServiceImpl implements CacheService {
	
    @Autowired
    ApplicationConfig config;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public void setValue(String key, Object value) {
        this.setValue(key, value, -1);
    }

    public void setValue(String key, Object value, int timeoutInSeconds) {
        if (timeoutInSeconds < 0) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, timeoutInSeconds, TimeUnit.SECONDS);
        }
    }

    public <T> T getValue(Class<T> clazz, String key) throws IllegalCastException {

        Object val = redisTemplate.opsForValue().get(key);
        if (val == null) {
            return null;
        }
        try {
            return (T) val;
        } catch (ClassCastException cce) {
            // Log the error.
        }
//        if (clazz.isInstance(val.getClass())) {
//            return (T)val;
//        }
        StringBuilder errorMsg = new StringBuilder("Fail to cast to ");
        errorMsg.append(clazz.toString());
        if (val != null) {
            errorMsg.append(". Object type is ");
            errorMsg.append(val.getClass().toString());
        }
        throw new IllegalCastException(errorMsg.toString());
    }

    public void addToSet(String key, Object... values) {
        this.addToSet(key, -1, values);
    }

    public void addToSet(String key, int timeoutInSeconds, Object... values) {
        redisTemplate.opsForSet().add(key, values);
        if (timeoutInSeconds >= 0) {
            redisTemplate.expire(key, timeoutInSeconds, TimeUnit.SECONDS);
        }
    }

    public Set<Object> getSetValues(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public void removeKey(String keyPattern) {
        Set<Object> keys = redisTemplate.keys(keyPattern);
        redisTemplate.delete(keys);
    }
}
