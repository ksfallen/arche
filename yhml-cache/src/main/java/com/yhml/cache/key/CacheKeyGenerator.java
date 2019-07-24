package com.yhml.cache.key;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.yhml.cache.annotaton.LocalCache;

/**
 * @author: Jfeng
 * @date: 2019-07-16
 */
public class LocalKeyGenerator extends AbstractKeyGenerator {

    @Override
    public String getKey(ProceedingJoinPoint pjp) {
        String clazzName = pjp.getTarget().getClass().getSimpleName();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        LocalCache localCache = method.getAnnotation(LocalCache.class);

        if (localCache == null) {
            return null;
        }

        return getKey(method, pjp.getArgs(), localCache.prefix(), localCache.keys(), localCache.delimiter());
    }
}
