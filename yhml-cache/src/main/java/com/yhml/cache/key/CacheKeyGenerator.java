package com.yhml.cache.key;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.yhml.cache.annotaton.LocalCache;
import com.yhml.cache.annotaton.RedisCache;

/**
 * @author: Jfeng
 * @date: 2019-07-16
 */
public class CacheKeyGenerator extends AbstractKeyGenerator {

    public String getKey(ProceedingJoinPoint pjp) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        RedisCache rc = method.getAnnotation(RedisCache.class);

        if (rc != null) {
            return getKey(method, pjp.getArgs(), rc.prefix(), rc.keys(), rc.delimiter());
        }

        LocalCache lc = method.getAnnotation(LocalCache.class);

        if (lc != null) {
            return getKey(method, pjp.getArgs(), lc.prefix(), lc.keys(), lc.delimiter());
        }

        return null;
    }
}
