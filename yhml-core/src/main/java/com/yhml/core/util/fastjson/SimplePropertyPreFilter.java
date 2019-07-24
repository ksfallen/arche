package com.yhml.core.util.fastjson;

import java.util.Collections;


/**
 * @author: Jfeng
 * @date: 2019-07-02
 */
public class SimplePropertyPreFilter extends com.alibaba.fastjson.serializer.SimplePropertyPreFilter {
    public SimplePropertyPreFilter(String... properties) {
        super(properties);
    }

    public SimplePropertyPreFilter(Class<?> clazz, String... properties) {
        super(clazz, properties);
    }

    public SimplePropertyPreFilter(Class<?> clazz, String[] excludeProperties, String... includeProperties) {
        super(clazz, includeProperties);
        if (excludeProperties != null && excludeProperties.length > 0) {
            Collections.addAll(getExcludes(), excludeProperties);
        }
    }

}
