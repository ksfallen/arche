package com.yhml.tools.money;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jfeng
 * @date 2020/5/25
 */
@AllArgsConstructor
@Getter
public enum CatalogEnum {

    FUND("公积金", "公积金", "公积金"),
    ;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * pro 的分类
     */
    private String proName;

    /**
     * wiz的分类
     */
    private String wizName;
}
