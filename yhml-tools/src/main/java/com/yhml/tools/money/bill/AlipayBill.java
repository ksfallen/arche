package com.yhml.tools.money.bill;

import com.yhml.tools.constants.TradeTypeEnum;
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
public class AlipayBill extends CsvModel {

    @Alias("交易号")
    private String tradeId;

    /**
     * 交易类型
     */
    @Alias("资金状态")
    private String tradeType;

    @Alias("交易状态")
    private String tradeStatus;

    @Alias("交易创建时间")
    private String tradeTime;

    @Alias("金额（元）")
    private String amount;

    @Alias("备注")
    private String remark;

    @Alias("账户")
    private String account;

    @Alias("交易对方")
    private String tradeName;

    @Alias("商品名称")
    private String productName;


    /**
     * 是否基金产品
     */
    public boolean isFund() {
        return productName.contains("蚂蚁财富");
    }

    public boolean isYueBao() {
        return productName.matches("余额宝-.*-收益发放");
    }

    /**
     * 自动还款-花呗
     */
    public boolean isHuaBei() {
        return productName.matches("自动还款-花呗.*账单");
    }

    /**
     * 卡转移余额宝
     */
    public boolean IsFundsTransfer() {
        return productName.matches("余额宝-单次转入") && TradeTypeEnum.FUNDS_TRANSFER.equals(tradeType);
    }
}
