package com.yhml.cache.lock;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2019-07-15
 */
@Slf4j
public class LockTemplate {
    private LockExecutor lockExecutor;

    public LockInfo lock(String key, long expire, long timeout, TimeUnit timeUnit) throws Exception {
        Assert.isTrue(timeout > 0L, "tryTimeout must more than 0");

        long start = System.currentTimeMillis();
        int acquireCount = 0;
        String value = UUID.randomUUID().toString();

        long millis = timeUnit.toMillis(timeout);

        while(System.currentTimeMillis() - start < millis) {
            boolean result = this.lockExecutor.lock(key, value, expire);
            ++acquireCount;
            if (result) {
                return new LockInfo(key, value, expire, millis, acquireCount);
            }

            Thread.sleep(50L);
        }

        log.info("lock failed, try {} times", acquireCount);
        return null;
    }

    public boolean unLock(LockInfo lockInfo) {
        return this.lockExecutor.unLock(lockInfo.getLockKey(), lockInfo.getLockValue());
    }

    public void setLockExecutor(LockExecutor lockExecutor) {
        this.lockExecutor = lockExecutor;
    }
}
