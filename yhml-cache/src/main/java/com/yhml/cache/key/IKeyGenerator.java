package com.yhml.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author: Jfeng
 * @date: 2018/7/26
 */
public interface AbstractCacheKeyGenerator {

    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}

