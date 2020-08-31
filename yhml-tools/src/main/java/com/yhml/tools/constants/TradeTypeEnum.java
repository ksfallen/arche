package com.yhml.tools.constants;

import java.util.Arrays;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    ASSETS_BUY("资产买入"),
    ASSETS_SOLD("资产卖出"),
    BALANCE("结余调整"),
    ;

    private String type;

    public static boolean notContains(String type) {
        Optional<TradeTypeEnum> op = Arrays.stream(TradeTypeEnum.values()).filter(t -> t.type.equals(type)).findFirst();
        return !op.isPresent();
    }

    public static boolean isConsume(String type) {
        return CONSUME.type.equals(type) || type.contains(CONSUME.type);
    }

    public static boolean isTransfer(String type) {
        return TRANSFER.getType().equals(type);
    }

    public static boolean isIncome(String type) {
        return INCOME.getType().equals(type) || type.contains(INCOME.type);
    }

    public static boolean isAssetsBuy(String type) {
        return TradeTypeEnum.ASSETS_BUY.getType().equals(type);
    }

    public static boolean isAssetsSold(String type) {
        return TradeTypeEnum.ASSETS_SOLD.getType().equals(type);
    }

    public static boolean isBalance(String type) {
        return  TradeTypeEnum.BALANCE.getType().equals(type);
    }
}
