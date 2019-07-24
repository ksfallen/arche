package com.yhml.cache.lock;

import java.util.Collections;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2019-07-15
 */
@Slf4j
public class RedisLockExecutor implements LockExecutor {
    // @formatter:off
    private static final RedisScript<String> SCRIPT_LOCK = new DefaultRedisScript(
            "return redis.call('set',KEYS[1],ARGV[1],'NX','PX', ARGV[2])", String.class);

    private static final RedisScript<String> SCRIPT_UNLOCK = new DefaultRedisScript(
            "if redis.call('get',KEYS[1]) == ARGV[1] then return tostring(redis.call('del', KEYS[1])==1) else return 'false' end", String.class);

    @Setter
    private RedisTemplate redisTemplate;

    public boolean lock(String lockKey, String lockValue, long acquireExpire) {
        Object lockResult = redisTemplate.execute(
                SCRIPT_LOCK,
                redisTemplate.getStringSerializer(),
                redisTemplate.getStringSerializer(),
                Collections.singletonList(lockKey),
                new Object[]{lockValue, String.valueOf(acquireExpire)}
                );

        return "OK".equals(lockResult);
    }

    public boolean unLock(String lockKey, String value) {
        Object releaseResult = redisTemplate.execute(
                SCRIPT_UNLOCK,
                redisTemplate.getStringSerializer(),
                redisTemplate.getStringSerializer(),
                Collections.singletonList(lockKey),
                new Object[]{value}
                );

        return Boolean.valueOf(releaseResult.toString());
    }

    // @formatter:on
}
