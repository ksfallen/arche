package com.yhml.lock;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: Jfeng
 * @date: 2019-07-15
 */
@Data
@AllArgsConstructor
public class LockInfo {
    private String lockKey;
    private String lockValue;
    private Long expire;


    private Long acquireTimeout;

    /**
     * 获取锁的次数
     */
    private int acquireCount = 0;
}
