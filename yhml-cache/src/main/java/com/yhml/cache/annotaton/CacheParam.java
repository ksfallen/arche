package com.yhml.cache.annotaton;

import java.lang.annotation.*;

/**
 * 缓存key 注解参数
 * @author: Jfeng
 * @date: 2018/7/26
 */
// @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {
    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}
