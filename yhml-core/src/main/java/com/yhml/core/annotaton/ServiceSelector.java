package com.yhml.core.annotaton;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author: Jfeng
 * @date: 2018/4/21
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface ServiceSelector {
    /**
     * 第一级的key
     */
    String key() default "";

    /**
     * 第二级 key
     */
    String subKey() default "";

    String version() default "1.0";
}
