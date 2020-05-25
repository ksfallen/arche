package com.yhml.tools.money;

import com.yhml.tools.model.CsvModel;
import cn.hutool.core.annotation.Alias;
import cn.hutool.core.bean.BeanUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jfeng
 * @date 2020/5/19
 */
@Getter
@Setter
@NoArgsConstructor
public class MoneyPro extends CsvModel {
    /**
     * 导入格式 yyyyMMdd HH:mm:ss
     */
    @Alias("日期")
    public String date;

    @Alias("款额")
    private String amount;

    @Alias("账户")
    private String account;

    @Alias("总额")
    private String totalAmount;

    @Alias("转账到")
    private String transfer;

    @Alias("结余")
    private String balance;

    @Alias("类别")
    private String catalog;

    @Alias("说明")
    private String desc;

    @Alias("交易类型")
    private String tradeType;

    @Alias("代理人")
    private String business;

    @Alias("支票号")
    private String checkNo;

    @Alias("种类")
    private String lable;


    public static MoneyPro build(MoneyWiz copy) {
        MoneyPro bean = new MoneyPro();
        BeanUtil.copyProperties(copy, bean);
        return bean;
    }


    /**
     * 费用
     */
    public String getCharge() {
        return "-" + getAmount();
    }

    public String getAmount() {
        return amount == null ? "" : amount.replace(",", "").replace("CN¥", "");
    }

    public boolean isConsume() {
        return TradeTypeEnum.CONSUME.getType().equals(getTradeType());
    }

    public boolean isTransfer() {
        return TradeTypeEnum.TRANSFER.getType().equals(getTradeType());
    }

    public boolean isIncome() {
        return TradeTypeEnum.INCOME.getType().equals(getTradeType());
    }
}
