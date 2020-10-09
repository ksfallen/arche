package com.yhml.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jfeng
 * @date 2020/7/14
 */
public class SimpleServiceHandler {
    private static Map<String, Object> CACHE = new ConcurrentHashMap<>();

    public static void addBean(String key, Object object) {
        CACHE.put(key, object);
    }

    public static <T> T getBean(String key, String subKey,  Class<T> clazz) {
        return getBean(key + subKey, clazz);
    }

    public static <T> T getBean(String key, Class<T> clazz) {
        return (T) CACHE.get(key);
    }

}
