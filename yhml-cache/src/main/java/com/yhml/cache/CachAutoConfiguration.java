package com.yhml.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.yhml.cache.aop.LocalCacheAspect;
import com.yhml.cache.aop.LockAnnotationAdvisor;
import com.yhml.cache.aop.LockInterceptor;
import com.yhml.cache.key.CacheKeyGenerator;
import com.yhml.cache.key.LockKeyGenerator;
import com.yhml.cache.lock.LockExecutor;
import com.yhml.cache.lock.LockTemplate;
import com.yhml.cache.lock.RedisLockExecutor;

@Configuration
public class CachAutoConfiguration {

    @Bean
    public LockKeyGenerator lockKeyGenerator() {
        return new LockKeyGenerator();
    }

    @Bean
    public CacheKeyGenerator cacheKeyGenerator() {
        return new CacheKeyGenerator();
    }

    @Bean
    public LocalCacheAspect localCacheAspect(CacheKeyGenerator cacheKeyGenerator) {
        return new LocalCacheAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass({RedisTemplate.class})
    public RedisLockExecutor lockExecutor(RedisTemplate redisTemplate) {
        RedisLockExecutor redisTemplateLockExecutor = new RedisLockExecutor();
        redisTemplateLockExecutor.setRedisTemplate(redisTemplate);
        return redisTemplateLockExecutor;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockTemplate lockTemplate(LockExecutor lockExecutor) {
        LockTemplate lockTemplate = new LockTemplate();
        lockTemplate.setLockExecutor(lockExecutor);
        return lockTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockAnnotationAdvisor lockAnnotationAdvisor(LockInterceptor lockInterceptor) {
        return new LockAnnotationAdvisor(lockInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public LockInterceptor lockInterceptor(LockTemplate lockTemplate) {
        LockInterceptor lockInterceptor = new LockInterceptor();
        lockInterceptor.setLockTemplate(lockTemplate);
        return lockInterceptor;
    }
}
