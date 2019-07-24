package com.yhml.cache.annotaton;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

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
public @interface RedisCache {

    /**
     * key的前缀
     */
    String prefix() default "";

    /**
     * 字段名称 el表达式
     */
    String[] keys() default {""};

    /**
     * 多个Key 默认分隔符（默认 .）
     * key1.key2.ke要
     */
    String delimiter() default ".";

    /**
     * key过期秒数,默认为10秒
     * @return
     */
    long expire() default 10L;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
