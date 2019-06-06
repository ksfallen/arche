package com.yhml.core.context;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2019-05-29
 */
@Slf4j
public final class ThreadContext {
    private static final ThreadLocal<Map<String, Object>> LOCAL = new ThreadLocal();

    static {
        LOCAL.set(new HashMap());
    }

    public static void put(String key, Object value) {
        Map<String, Object> ctx = (Map) LOCAL.get();
        if (ctx == null) {
            ctx = new HashMap();
            LOCAL.set(ctx);
        }
        ctx.put(key, value);
    }

    public static <T> T get(String key) {
        Map<String, Object> ctx = (Map) LOCAL.get();
        if (ctx == null) {
            return null;
        }
        return (T) ctx.get(key);
    }

    public static String getStr(String key) {
        Map<String, Object> ctx = (Map) LOCAL.get();
        if (ctx == null) {
            return null;
        }
        return (String) ctx.get(key);
    }

    public static Integer getInt(String key) {
        return Integer.valueOf(getStr(key));
    }

    public static Long getLong(String key) {
        return Long.valueOf(getStr(key));
    }

    public static Map<String, Object> getContext() {
        Map<String, Object> ctx = (Map) LOCAL.get();
        if (ctx == null) {
            return null;
        }
        return ctx;
    }

    public static void remove(String key) {
        Map<String, Object> ctx = (Map) LOCAL.get();
        if (ctx != null) {
            ctx.remove(key);
        }
    }

    public static boolean contains(String key) {
        Map<String, Object> ctx = (Map) LOCAL.get();
        if (ctx != null) {
            return ctx.containsKey(key);
        }
        return false;
    }

    public static void clean() {
        LOCAL.remove();
    }

    public static void init() {
        LOCAL.set(new HashMap());
    }
}
