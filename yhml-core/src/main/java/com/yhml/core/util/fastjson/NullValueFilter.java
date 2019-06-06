package com.yhml.core.util.fastjson;


import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/12/1
 */
public class NullValueFilter implements ValueFilter {

    @Override
    public Object process(Object source, String name, Object value) {
        return value == null ? "" : value;
    }
}
