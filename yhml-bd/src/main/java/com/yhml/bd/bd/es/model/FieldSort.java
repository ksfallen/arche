package com.yhml.bd.bd.es.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by lvhantai on 2018/2/2.
 * 排序类型枚举
 */
@AllArgsConstructor
@Getter
public enum FieldSort {
    TIME("createDate"), PRICE("price"), SALES("sales");

    private String field;

    @Override
    public String toString() {
        return this.field;
    }
}
