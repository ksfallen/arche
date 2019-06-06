package com.yhml.bd.bd.lock.redis;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁操作模板
 *
 * @author: Jianfeng.Hu
 * @date: 2017/12/7
 */
public interface DistributedLockTemplate {

    /**
     * 使用分布式锁，使用锁默认超时时间。
     *
     * @param callback
     * @return
     */
    <T> T lock(DistributedLockCallback<T> callback);

    /**
     * 使用分布式锁。自定义锁的超时时间
     *
     * @param callback
     * @param leaseTime 锁超时时间。超时后自动释放锁。
     * @param timeUnit
     * @return
     */
    <T> T lock(DistributedLockCallback<T> callback, long leaseTime, TimeUnit timeUnit);
}
