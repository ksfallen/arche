package com.yhml.cache.annotaton;

import java.lang.annotation.*;

/**
 * 缓存注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LocalCache {

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

}
