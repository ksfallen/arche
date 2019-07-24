package com.yhml.cache.annotaton;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@LocalCache
public @interface LockAction {
    /**
     * 锁key的前缀
     */
    String prefix() default "";

    /**
     * 字段名称 el表达式
     * @return
     */
    String[] keys() default {""};

    /**
     * key过期秒数,默认为10秒
     * @return
     */
    long expire() default 10L;

    /**
     * 获取key超时,默认为5秒
     */
    long timeout() default 5L;

    /**
     * 时间单位
     *
     * @return 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 多个Key 默认分隔符（默认 .）
     * key1.key2.ke要
     */
    String delimiter() default ".";



}
