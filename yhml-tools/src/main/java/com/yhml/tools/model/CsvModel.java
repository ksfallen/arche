package com.yhml.tools.model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.annotation.Alias;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;

import lombok.Getter;

import static java.util.stream.Collectors.toList;

/**
 * @author Jfeng
 * @date 2020/5/19
 */
@Getter
public class CsvModel<T> {

    public String[] getHeader() {
        Field[] fields = getClass().getDeclaredFields();
        List<String> collect = Arrays.stream(fields).map(field -> {
            Alias alias = field.getAnnotation(Alias.class);
            return alias == null ? field.getName() : alias.value();
        }).collect(toList());

        return ArrayUtil.toArray(collect, String.class);
    }


    public String getCsvData() {
        Field[] fields = getClass().getDeclaredFields();
        List<String> collect = Arrays.stream(fields).map(field -> String.valueOf(ReflectUtil.getFieldValue(this, field))).collect(toList());
        return CollectionUtil.join(collect, ",");
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
