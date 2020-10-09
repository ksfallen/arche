package com.yhml.cache.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.yhml.cache.annotaton.RedisCache;
import com.yhml.cache.key.CacheKeyGenerator;
import com.yhml.core.util.JsonUtil;
import com.yhml.core.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2018/7/26
 */
@Aspect
@Slf4j
public class RedisCacheAspect {
    @Autowired
    private CacheKeyGenerator keyGenerator;

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("execution(public * *(..)) && @annotation(com.yhml.cache.annotaton.RedisCache)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        String clazzName = pjp.getTarget().getClass().getSimpleName();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        RedisCache redisCache =  method.getAnnotation(RedisCache.class);

        String key = keyGenerator.getKey(pjp);
        Object ret = null;

        if (StringUtil.isNotBlank(key)) {
            // 如果找不到返回null，找到就返回相应的值
            ret = redisTemplate.boundValueOps(key).get();

            if (ret != null) {
                log.info("get form redis cache === {}.{}, key:{}", clazzName, method.getName(), key);
                return ret;
            }
        }

        ret = pjp.proceed();
        redisTemplate.opsForValue().set(key, JsonUtil.toJson(ret), redisCache.expire(), redisCache.timeUnit());

        return ret;
    }


}
