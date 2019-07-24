package com.yhml.core.util;


import org.springframework.cglib.core.Converter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2019-07-08
 */
@Slf4j
public class StringConverter implements Converter {

    public static StringConverter converter = new StringConverter();

    /**
     * @param value   源对象属性的值
     * @param target  目标对象属性的类
     * @param context 目标对象setter方法名
     * @return
     */
    @Override
    public Object convert(Object value, Class target, Object context) {

        log.debug("context:{}, value:{} type{}:", context, value, target);

        if (value instanceof Integer) {
            return value == null ? "" : String.valueOf(value);
        }
        if (value instanceof String) {
            return value == null ? "" : value;
        }

        return null;
    }
}
