package com.yhml.cache.aop;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yhml.cache.key.CacheKeyGenerator;
import com.yhml.core.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2018/7/26
 */
@Aspect
@Slf4j
public class LocalCacheAspect {

    // @formatter:off
    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            .maximumSize(1000)                      // 最大缓存 100 个
            .expireAfterWrite(5, TimeUnit.SECONDS)  // 设置写缓存后 5 秒钟过期
            .build();
    // @formatter:on

    @Autowired
    private CacheKeyGenerator keyGenerator;

    @Pointcut("execution(public * *(..)) && @annotation(com.yhml.cache.annotaton.LocalCache)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        String key = keyGenerator.getKey(pjp);

        String clazzName = pjp.getTarget().getClass().getSimpleName();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        Object ret = null;

        if (StringUtil.isNotBlank(key)) {
            // 如果找不到返回null，找到就返回相应的值
            ret = CACHES.getIfPresent(key);

            if (ret != null) {
                log.info("get form local cache === {}.{}, key:{}", clazzName, method.getName(), key);
                return ret;
            }
        }

        ret = pjp.proceed();
        CACHES.put(key, ret);
        // 删除
        // CACHES.invalidate(key);
        return ret;
    }


}
