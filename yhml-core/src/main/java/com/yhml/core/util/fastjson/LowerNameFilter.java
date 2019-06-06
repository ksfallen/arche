package com.yhml.core.util.fastjson;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.serializer.NameFilter;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/11/8
 */
public class LowerNameFilter implements NameFilter {

    @Override
    public String process(Object source, String name, Object value) {
        return StringUtils.isEmpty(name) ? name : name.toLowerCase();
    }
}
