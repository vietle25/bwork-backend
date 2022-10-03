package com.ieltshub.utility;

/**
 * @author tuannd
 * @date 18/07/2016
 * @since 1.0
 */
public class CacheUtility {
    /**
     * create cache key for user
     * @param userId
     * @return
     * @since 1.0
     */
    public static String createCacheUserKey(Long userId) {
        return "token" + String.valueOf(userId);
    }
}
