package com.yhml.cache.aop;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yhml.cache.annotaton.LocalCache;
import com.yhml.core.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2018/7/26
 */
@Aspect
@Component
@Slf4j
public class LockCacheAspect {

    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            // 最大缓存 100 个
            .maximumSize(1000)
            // 设置写缓存后 5 秒钟过期
            .expireAfterWrite(5, TimeUnit.SECONDS).build();


    @Pointcut("execution(public * *(..)) && @annotation(com.yhml.cache.annotaton.LocalCache)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        String clazzName = pjp.getTarget().getClass().getSimpleName();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        LocalCache annotation = method.getAnnotation(LocalCache.class);

        String key = getKey(annotation.key(), pjp.getArgs());

        if (!StringUtil.isEmpty(key)) {
            Object ret = CACHES.getIfPresent(key);

            if (ret != null) {
                log.info("local cache === {}.{},key:{}", clazzName, method.getName(), key);
                return ret;
                // throw new BaseException(ERROR_REPEAT_SUBMIT);
            }

            // 如果是第一次请求,就将 key 当前对象压入缓存中
            CACHES.put(key, key);
        }

        try {
            return pjp.proceed();
        } finally {
            // 删除
            CACHES.invalidate(key);
        }
    }

    /**
     * key 的生成策略,如果想灵活可以写成接口与实现类的方式
     *
     * @param keyExpress 表达式
     * @param args       参数
     * @return 生成的key
     */
    private String getKey(String keyExpress, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            keyExpress = keyExpress.replace("arg[" + i + "]", args[i].toString());
        }
        return keyExpress;
    }

}
