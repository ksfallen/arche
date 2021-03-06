package com.yhml.bd.bd.es.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yhml.bd.bd.es.enums.QueryMethod;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SearchInfo {

    boolean isFilter() default true;

    QueryMethod method();

    RangeCompare[] rangeInfo() default {};
}
