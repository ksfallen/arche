package com.yhml.tools.money.bill;

import com.yhml.tools.model.CsvModel;
import cn.hutool.core.annotation.Alias;

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
public class MoneyWiz extends CsvModel {
    /**
     * 发送交易的账户
     */
    @Alias("账户")
    private String account;

    /**
     * 转入的账户
     */
    @Alias("转账")
    private String toAccount = "";

    @Alias("描述")
    private String desc;

    @Alias("交易对象")
    private String business;

    @Alias("分类")
    private String catalog;

    @Alias("日期")
    private String tradeTime;

    @Alias("时间")
    private String time;

    @Alias("金额")
    private String amount;

    // @Alias("金额（收入）")
    // private String income = "";
    //
    // @Alias("金额（费用）")
    // private String charge = "";

    @Alias("支票号码")
    private String checkNo;

    @Alias("标签")
    private String lable = "个人";

    @Alias("备忘")
    private String remark = "";

}
