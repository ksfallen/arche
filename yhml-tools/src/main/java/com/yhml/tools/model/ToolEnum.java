package com.yhml.tools.model;

import com.yhml.tools.money.MoneyRunner;
import com.yhml.tools.ssr.SsrRunner;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jfeng
 * @date 2020/5/20
 */
@AllArgsConstructor
@Getter
public enum ToolEnum {
    ssr(SsrRunner.class),
    money(MoneyRunner.class),

    ;
    private Class type;

}
