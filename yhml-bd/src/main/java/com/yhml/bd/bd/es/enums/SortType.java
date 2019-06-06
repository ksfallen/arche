package com.yhml.bd.bd.es.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum SortType {

    TIME("createDate");

    private String code;

    public static SortType formCode(String code) {
        return Arrays.stream(SortType.values()).filter(order -> order.code.equals(code)).findFirst().orElse(null);
    }

    public static void main(String[] args) {
        System.out.println(formCode("DESC"));
    }
}
