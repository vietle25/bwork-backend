package com.evowork.service;

import java.util.Set;

import com.evowork.exception.IllegalCastException;

/**
 * @author tuannd
 * @date 18/07/2016
 * @since 1.0
 */
public interface CacheService {
    /**
     * set value on cache for key
     * @param key
     * @param value
     * @since 1.0
     */
    void setValue(String key, Object value);

    /**
     * set value on cache for 'key' with timeout
     * @param key
     * @param value
     * @param timeoutInSeconds cache timeout on second
     * @since 1.0
     */
    void setValue(String key, Object value, int timeoutInSeconds);

    /**
     * get value on cache
     * @param clazz
     * @param key
     * @param <T>
     * @return
     */
    <T> T getValue(Class<T> clazz, String key) throws IllegalCastException;

    void addToSet(String key, Object... values);

    void addToSet(String key, int timeoutInSeconds, Object... values);

    Set<Object> getSetValues(String key);

    void removeKey(String keyPattern);
}
