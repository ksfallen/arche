package com.yhml.cache.annotaton;

import java.lang.annotation.*;

/**
 * @author: Jfeng
 * @date: 2018/4/21
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token {
}