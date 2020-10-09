package com.yhml.tools.constants;

import cn.hutool.core.util.StrUtil;

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
    GJJ("", "公积金", "公积金"),
    FUND("", "基金", "基金"),
    LC("", "理财", "理财"),

    GW("", "购物", "超市"),
    LF("", "理发", "造型"),
    YC("", "衣服","优衣库"),

    ZC("杭州", "正餐", "饭|享食客|食堂|美团|馄饨"),
    DC("杭州", "打车", "滴滴"),
    GT("杭州", "高铁", "携程|火车票|铁路"),

    DF("家", "电费", "电费"),
    SF("家", "水费", "水费"),

    WJ("小孩", "玩具", "玩具"),
    YY("小孩", "小孩:医疗", "妇幼保健院"),
    LY("小孩", "游乐园","儿童乐园"),

    TC("", "停车费","停车场"),
    ;

    /**
     * pro 的类别
     */
    private String label;

    /**
     * pro 的分类
     */
    private String catalog;

    /**
     * 账户名, 交易名称, 交易对象
     */
    private String type;

    public static String getWizCatalog(String account) {
        Optional<CatalogEnum> opt = Arrays.stream(CatalogEnum.values()).filter(anEnum -> account.contains(anEnum.getType())).findFirst();
        return opt.orElse(FUND).getCatalog();
    }

    public static CatalogEnum getProCatalog(String... accounts) {
        if (accounts == null) {
            return null;
        }
        for (String name : accounts) {
            CatalogEnum  catalog = getCatalogEnum(name);
            if (catalog != null) {
                return catalog;
            }
        }
        return null;
    }

    public static CatalogEnum getCatalogEnum(String name) {
        Optional<CatalogEnum> opt = Arrays.stream(CatalogEnum.values())
                .filter(anEnum -> Arrays.stream(anEnum.getType().split("\\|")).anyMatch(type -> StrUtil.contains(name, type.trim())))
                .findFirst();
        return opt.orElse(null);
    }
}
