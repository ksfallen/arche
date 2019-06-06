package com.yhml.bd.bd.lock.redis;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * Single Instance mode 分布式锁模板
 *
 * @author: Jianfeng.Hu
 * @date: 2017/12/7
 */
@Setter
@AllArgsConstructor
public class SingleDistributedLockTemplate implements DistributedLockTemplate {

    private static final long DEFAULT_TIMEOUT = 5;

    private RedissonClient redisson;

    @Override
    public <T> T lock(DistributedLockCallback<T> callback) {
        return lock(callback, DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }

    @Override
    public <T> T lock(DistributedLockCallback<T> callback, long leaseTime, TimeUnit timeUnit) {
        RLock lock = null;
        try {
            lock = redisson.getLock(callback.getLockName());
            lock.lock(leaseTime, timeUnit);
            return callback.process();
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }
}
