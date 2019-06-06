package com.yhml.bd.bd.es.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Jfeng
 * @date: 2018/8/16
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ESField {

    String type() default "keyword";

    boolean index() default true;

    String analyzer() default "";

    boolean ignore() default false;
}
