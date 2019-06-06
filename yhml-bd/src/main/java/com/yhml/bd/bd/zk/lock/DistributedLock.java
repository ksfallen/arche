package com.yhml.bd.bd.zk.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 * @author: Jianfeng.Hu
 * @date: 2017/11/30
 */
public interface DistributedLock {

    /**
     * 尝试获取锁
     *
     * @throws Exception
     */
    void acquire() throws Exception;

    /**
     * 获取锁, 直到超时
     *
     * @param time
     * @param timeUnit
     * @return
     * @throws Exception
     */
    boolean acquire(long time, TimeUnit timeUnit) throws Exception;

    /**
     * 释放锁
     *
     * @return
     * @throws Exception
     */
    void release() throws Exception;
}
