package com.yhml.bd.bd.lock.redis;

/**
 *  分布式锁回调接口
 * @author: Jianfeng.Hu
 * @date: 2017/12/7
 */
public interface DistributedLockCallback<T> {

    /**
     * 调用者必须在此方法中实现需要加分布式锁的业务逻辑
     *
     * @return
     */
    T process();

    /**
     * 得到分布式锁名称
     *
     * @return
     */
    String getLockName();


}
