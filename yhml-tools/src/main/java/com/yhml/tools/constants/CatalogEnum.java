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
public enum CatalogEnum {
    FUND("投资: 基金", "基金"),
    LC("理财", "理财"),
    LX("利息", "利息"),
    ACCUMULATION("公积金", "公积金"),
    GW("日常:购物", "超市"),
    ZC("吃喝:正餐", "饭|享食客|食堂|美团"),
    DC("出行:打车", "滴滴"),
    GT("出行:高铁", "携程|火车票"),
    ;

    /**
     * pro 的分类
     */
    private String catalog;


    /**
     * 账户名
     */
    private String account;


    public static String getWizCatalog(String account) {
        Optional<CatalogEnum> opt = Arrays.stream(CatalogEnum.values()).filter(anEnum -> account.contains(anEnum.getAccount())).findFirst();
        return opt.orElse(FUND).getCatalog();
    }

    public static String getCatalog(String account) {
        Optional<CatalogEnum> opt = Arrays.stream(CatalogEnum.values()).filter(anEnum -> {
            if (!anEnum.getAccount().contains("|")) {
                return account.contains(anEnum.getAccount());
            }
            return Arrays.stream(anEnum.getAccount().split("\\|")).anyMatch(str -> account.contains(str.trim()));
        }).findFirst();

        return opt.isPresent() ? opt.get().getCatalog() : "";
    }
}
