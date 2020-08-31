package com.yhml.tools.model;

import cn.hutool.core.annotation.Alias;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

import static java.util.stream.Collectors.toList;

/**
 * @author Jfeng
 * @date 2020/5/19
 */
@Getter
public class CsvModel<T> {

    public String[] toHeader() {
        Field[] fields = getClass().getDeclaredFields();
        List<String> collect = Arrays.stream(fields).map(field -> {
            Alias alias = field.getAnnotation(Alias.class);
            return alias == null ? field.getName() : alias.value();
        }).collect(toList());

        return ArrayUtil.toArray(collect, String.class);
    }

    public String toCsvData() {
        Field[] fields = getClass().getDeclaredFields();
        List<String> collect = Arrays.stream(fields).map(field -> {
            Object value = ReflectUtil.getFieldValue(this, field);
            return value == null ? "" : String.valueOf(value);
        }).collect(toList());
        return CollectionUtil.join(collect, ",");
    }

    @Override
    public String toString() {
        JSON json = JSONUtil.parse(this, JSONConfig.create().setOrder(true));
        return json.toString();
    }
}
