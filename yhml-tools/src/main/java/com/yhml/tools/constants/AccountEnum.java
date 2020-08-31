package com.yhml.tools.constants;

import java.util.Arrays;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jfeng
 * @date 2020/5/25
 */
@AllArgsConstructor
@Getter
public enum AccountEnum {
    YUEBAO("余额宝", "余额宝"),
    CMB1799("招商银行 (1799)", "招行1799"),
    CMB2071("招商银行 (2071)", "招行12071"),
    ;


    /**
     * 支付宝账户名
     */
    private String alipayAccount;

    /**
     * pro 的分类
     */
    private String proAccount;

    public static String getProAccount(String alipayAccount) {
        Optional<AccountEnum> optional = Arrays.stream(AccountEnum.values()).filter(anEnum -> anEnum.alipayAccount.equals(alipayAccount)).findFirst();
        return optional.isPresent() ? optional.get().proAccount : "";
    }

}
