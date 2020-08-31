package com.yhml.tools.money.bill;

import com.yhml.tools.constants.TradeTypeEnum;
import com.yhml.tools.model.CsvModel;
import cn.hutool.core.annotation.Alias;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

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
    public String tradeTime;

    @Alias("款额")
    private String amount;

    @Alias("账户")
    private String account;

    @Alias("总额")
    private String totalAmount;

    @Alias("转账到")
    private String toAccount;

    @Alias("资产")
    private String assets;

    @Alias("结余")
    private String balance;

    @Alias("类别")
    private String catalog;

    @Alias("说明")
    private String desc;

    @Alias("交易类型")
    private String tradeType;

    /**
     * 交易对象
     */
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

    public String getCatalog() {
        return this.catalog.replace(" ", "");
    }

    /**
     * 费用
     */
    // public String getCharge() {
    //     return "-" + getAmount();
    // }

    public String getAmount() {
        String amt =  amount == null ? "" : amount.replace(",", "").replace("CN¥", "");
        if (StrUtil.startWith(amt, "(") && StrUtil.endWith(amt, ")")) {
            amt = "-" + amt.replaceAll("[()]", "");
        }
        return amt;
    }

    public boolean isConsume() {
        return TradeTypeEnum.isConsume(this.tradeType);
    }

    public boolean isTransfer() {
        return TradeTypeEnum.isTransfer(tradeType);
    }

    public boolean isIncome() {
        return TradeTypeEnum.isIncome(tradeType);
    }

    public boolean isAssetsBuy() {
        return TradeTypeEnum.isAssetsBuy(tradeType);
    }

    public boolean isAssetsSold() {
        return TradeTypeEnum.isAssetsSold(tradeType);
    }

    public boolean isBalance() {
        return TradeTypeEnum.isBalance(tradeType);
    }
}
