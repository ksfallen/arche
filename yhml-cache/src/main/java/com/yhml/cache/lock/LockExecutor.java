package com.yhml.lock;


/**
 * @author: Jfeng
 * @date: 2019-07-15
 */
public interface LockExecutor {
    boolean lock(String key, String value, long expire);

    boolean unLock(LockInfo lockInfo);
}
