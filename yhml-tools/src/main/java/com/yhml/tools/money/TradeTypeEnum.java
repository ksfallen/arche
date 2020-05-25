package com.yhml.tools.money;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Jfeng
 * @date 2020/5/20
 */
@AllArgsConstructor
@Getter
public enum TradeTypeEnum {
    CONSUME("支出"),
    TRANSFER("转账"),
    INCOME("收入"),
    ;

    private String type;

    public static boolean notContains(String type) {
        Optional<TradeTypeEnum> op = Arrays.stream(TradeTypeEnum.values()).filter(t -> t.type.equals(type)).findFirst();
        return !op.isPresent();
    }
}
