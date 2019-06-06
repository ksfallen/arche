package com.yhml.bd.bd.es.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.simple.common.es.enums.CompareOperation;


@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RangeCompare {
    String queryName();

    CompareOperation operation();

    boolean include() default false;
}
