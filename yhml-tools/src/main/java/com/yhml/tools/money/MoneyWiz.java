package com.yhml.tools.money;

import com.yhml.tools.model.CsvModel;
import cn.hutool.core.annotation.Alias;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import lombok.*;

/**
 * @author Jfeng
 * @date 2020/5/19
 */
@Getter
@Setter
@NoArgsConstructor
public class MoneyWiz extends CsvModel {
    @Alias("账户")
    private String account;

    @Alias("转账")
    private String transfer = "";

    @Alias("描述")
    private String desc;

    @Alias("交易对象")
    private String business;

    @Alias("分类")
    private String catalog;

    @Alias("日期")
    private String date;

    @Alias("时间")
    private String time;

    @Alias("金额（收入）")
    private String income = "";

    @Alias("金额（费用）")
    private String charge = "";

    @Alias("支票号码")
    private String checkNo;

    @Alias("标签")
    private String lable;

    public static MoneyWiz build(MoneyPro copy) {
        MoneyWiz bean = new MoneyWiz();
        bean.setAccount(copy.getAccount());
        bean.setDesc(copy.getDesc());
        bean.setBusiness(copy.getBusiness());
        bean.setCheckNo(copy.getCheckNo());
        bean.setLable(copy.getLable());
        bean.setCatalog(copy.getCatalog().replace(": ", " > "));
        String dateStr = StrUtil.removeAll(copy.getDate().trim(), new char[]{'年', '日', '月'});
        DateTime dateTime = DateUtil.parse(dateStr, "yyyyMdd HH:mm");
        bean.setDate(dateTime.toString("yyyy/MM/dd"));
        bean.setTime(dateTime.toString("HH:mm"));

        if (copy.isConsume()) {
            bean.setCharge(copy.getCharge());
        }

        if (copy.isTransfer()) {
            bean.setTransfer(copy.getTransfer());
            bean.setCharge(copy.getCharge());
            String desc = StrUtil.format("{} 转入 {} ({})", copy.getAccount(), copy.getTransfer(), copy.getDesc());
            bean.setDesc(desc);
        }

        if (copy.isIncome()) {
            bean.setIncome(copy.getAmount());
        }

        return bean;
    }
}
