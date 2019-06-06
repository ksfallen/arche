package com.yhml.cache.annotaton;

import java.lang.annotation.*;

/**
 * 本地分布式锁
 *
 * @author: Jfeng
 * @date: 2018/4/21
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RdisCache {
    String key() default "";

    /**
     * 过期时间
     *
     * @author fly
     */
    long expire() default 0L;
}
