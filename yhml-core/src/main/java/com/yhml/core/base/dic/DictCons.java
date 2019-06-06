package com.yhml.core.base.dic;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

/**
 * 本文件由ares studio 自动生成, 不要直接修改本文件的内容
 * @author zjhua
 *
 */
public final class DictCons { 

    public static final String LF_APP_ID__TMS = "1134001";          //数链科技
    public static final String NAME__LF_APP_ID__TMS = "数链科技";          //数链科技

    public static final String PAY_TYPE__TF_ACCOUNT = "TF_ACCOUNT";          //账户余额
    public static final String NAME__PAY_TYPE__TF_ACCOUNT = "账户余额";          //账户余额
    public static final String PAY_TYPE__CASH = "CASH";          //现金
    public static final String NAME__PAY_TYPE__CASH = "现金";          //现金
    public static final String PAY_TYPE__BANK = "BANK";          //银行账户
    public static final String NAME__PAY_TYPE__BANK = "银行账户";          //银行账户
    public static final String PAY_TYPE__OTHER = "OTHER";          //其它
    public static final String NAME__PAY_TYPE__OTHER = "其它";          //其它

    public static final String FUND_ACCOUNT_STATUS__NORMAL = "NORMAL";          //正常
    public static final String NAME__FUND_ACCOUNT_STATUS__NORMAL = "正常";          //正常
    public static final String FUND_ACCOUNT_STATUS__FROZEN = "FROZEN";          //冻结
    public static final String NAME__FUND_ACCOUNT_STATUS__FROZEN = "冻结";          //冻结

    public static final String LF_ACCOUNT_STATUS__NORMAL = "NORMAL";          //正常
    public static final String NAME__LF_ACCOUNT_STATUS__NORMAL = "正常";          //正常
    public static final String LF_ACCOUNT_STATUS__FROZEN = "FROZEN";          //冻结
    public static final String NAME__LF_ACCOUNT_STATUS__FROZEN = "冻结";          //冻结

    public static final String BOOL_INT__TRUE = "1";          //是
    public static final String NAME__BOOL_INT__TRUE = "是";          //是
    public static final String BOOL_INT__FALSE = "0";          //否
    public static final String NAME__BOOL_INT__FALSE = "否";          //否

    public static final String BUSINESS_PART_ID__TF56 = "TF56";          //智慧物流
    public static final String NAME__BUSINESS_PART_ID__TF56 = "智慧物流";          //智慧物流
    public static final String BUSINESS_PART_ID__FY56 = "FY56";          //飞扬
    public static final String NAME__BUSINESS_PART_ID__FY56 = "飞扬";          //飞扬
    public static final String BUSINESS_PART_ID__SDCN = "SDCN";          //天龙
    public static final String NAME__BUSINESS_PART_ID__SDCN = "天龙";          //天龙
    public static final String BUSINESS_PART_ID__SYSTRONG = "SYSTRONG";          //时创
    public static final String NAME__BUSINESS_PART_ID__SYSTRONG = "时创";          //时创
    public static final String BUSINESS_PART_ID__SUONUOTECH = "SUONUOTECH";          //硕诺
    public static final String NAME__BUSINESS_PART_ID__SUONUOTECH = "硕诺";          //硕诺
    public static final String BUSINESS_PART_ID__CHEMANMAN = "CHEMANMAN";          //车满满
    public static final String NAME__BUSINESS_PART_ID__CHEMANMAN = "车满满";          //车满满
    public static final String BUSINESS_PART_ID__LANQIAO = "LANQIAO";          //蓝桥
    public static final String NAME__BUSINESS_PART_ID__LANQIAO = "蓝桥";          //蓝桥

    public static final String BONUS_STATUS__UN_BONUS = "UN_BONUS";          //待分佣
    public static final String NAME__BONUS_STATUS__UN_BONUS = "待分佣";          //待分佣
    public static final String BONUS_STATUS__BONUSING = "BONUSING";          //分佣中
    public static final String NAME__BONUS_STATUS__BONUSING = "分佣中";          //分佣中
    public static final String BONUS_STATUS__SUCCESS = "SUCCESS";          //分佣成功
    public static final String NAME__BONUS_STATUS__SUCCESS = "分佣成功";          //分佣成功
    public static final String BONUS_STATUS__FAIL = "FAIL";          //分佣失败
    public static final String NAME__BONUS_STATUS__FAIL = "分佣失败";          //分佣失败

    public static final String ACTIVE_STATE__YES = "Y";          //是
    public static final String NAME__ACTIVE_STATE__YES = "是";          //是
    public static final String ACTIVE_STATE__NO = "N";          //否
    public static final String NAME__ACTIVE_STATE__NO = "否";          //否

    public static final String PAY_STATUS__PAYING = "1";          //支付中
    public static final String NAME__PAY_STATUS__PAYING = "支付中";          //支付中
    public static final String PAY_STATUS__PAY_SUCCESS = "2";          //支付成功
    public static final String NAME__PAY_STATUS__PAY_SUCCESS = "支付成功";          //支付成功
    public static final String PAY_STATUS__PAY_FAIL = "3";          //支付失败
    public static final String NAME__PAY_STATUS__PAY_FAIL = "支付失败";          //支付失败
    public static final String PAY_STATUS__APPLYING = "4";          //受理中
    public static final String NAME__PAY_STATUS__APPLYING = "受理中";          //受理中

    public static final String FIN_ORDER_STATUS__APPLYING = "1";          //申请中
    public static final String NAME__FIN_ORDER_STATUS__APPLYING = "申请中";          //申请中
    public static final String FIN_ORDER_STATUS__ACCEPT = "2";          //已受理
    public static final String NAME__FIN_ORDER_STATUS__ACCEPT = "已受理";          //已受理
    public static final String FIN_ORDER_STATUS__LOANING = "3";          //放款中
    public static final String NAME__FIN_ORDER_STATUS__LOANING = "放款中";          //放款中
    public static final String FIN_ORDER_STATUS__LOAN_FAIL = "4";          //放款失败
    public static final String NAME__FIN_ORDER_STATUS__LOAN_FAIL = "放款失败";          //放款失败
    public static final String FIN_ORDER_STATUS__LOAN_SUCCESS = "5";          //放款成功
    public static final String NAME__FIN_ORDER_STATUS__LOAN_SUCCESS = "放款成功";          //放款成功
    public static final String FIN_ORDER_STATUS__CANCEL = "6";          //作废
    public static final String NAME__FIN_ORDER_STATUS__CANCEL = "作废";          //作废
    public static final String FIN_ORDER_STATUS__REPAY_SUCCESS = "7";          //还款成功
    public static final String NAME__FIN_ORDER_STATUS__REPAY_SUCCESS = "还款成功";          //还款成功

    public static final String PARTY_TYPE__PERSONAL = "个人";          //个人
    public static final String NAME__PARTY_TYPE__PERSONAL = "个人";          //个人
    public static final String PARTY_TYPE__ENTERPRISE = "企业";          //企业
    public static final String NAME__PARTY_TYPE__ENTERPRISE = "企业";          //企业

    public static final String STATEMENT_MODE__LOAN = "1";          //放款时出账
    public static final String NAME__STATEMENT_MODE__LOAN = "放款时出账";          //放款时出账
    public static final String STATEMENT_MODE__FIXED = "2";          //按账期出账
    public static final String NAME__STATEMENT_MODE__FIXED = "按账期出账";          //按账期出账

    public static final String PARTY_ROLE__DRIVER = "driver";          //司机
    public static final String NAME__PARTY_ROLE__DRIVER = "司机";          //司机
    public static final String PARTY_ROLE__CONSIGNER = "consigner";          //发货人
    public static final String NAME__PARTY_ROLE__CONSIGNER = "发货人";          //发货人
    public static final String PARTY_ROLE__CONSIGNEE = "consignee";          //收货人
    public static final String NAME__PARTY_ROLE__CONSIGNEE = "收货人";          //收货人
    public static final String PARTY_ROLE__EXPRESS = "express";          //物流公司
    public static final String NAME__PARTY_ROLE__EXPRESS = "物流公司";          //物流公司
    public static final String PARTY_ROLE__TRANSIT = "transit";          //中转公司
    public static final String NAME__PARTY_ROLE__TRANSIT = "中转公司";          //中转公司
    public static final String PARTY_ROLE__TMS = "tms";          //TMS系统
    public static final String NAME__PARTY_ROLE__TMS = "TMS系统";          //TMS系统
    public static final String PARTY_ROLE__AGENCY = "agency";          //代理商
    public static final String NAME__PARTY_ROLE__AGENCY = "代理商";          //代理商
    public static final String PARTY_ROLE__LF = "lf";          //金融产品托管账号
    public static final String NAME__PARTY_ROLE__LF = "金融产品托管账号";          //金融产品托管账号

    public static final String SETTLE_ACCOUNT_TYPE__TF_ACCOUNT = "TF_ACCOUNT";          //传化钱包
    public static final String NAME__SETTLE_ACCOUNT_TYPE__TF_ACCOUNT = "传化钱包";          //传化钱包
    public static final String SETTLE_ACCOUNT_TYPE__BANK_ACCOUNT = "BANK_ACCOUNT";          //银行卡
    public static final String NAME__SETTLE_ACCOUNT_TYPE__BANK_ACCOUNT = "银行卡";          //银行卡
    public static final String SETTLE_ACCOUNT_TYPE__ALIPAY = "ALIPAY";          //支付宝
    public static final String NAME__SETTLE_ACCOUNT_TYPE__ALIPAY = "支付宝";          //支付宝
    public static final String SETTLE_ACCOUNT_TYPE__WECHAT = "WECHAT";          //微信
    public static final String NAME__SETTLE_ACCOUNT_TYPE__WECHAT = "微信";          //微信

    public static final String SETTLE_STATUS__TODO = "未结算";          //待结算
    public static final String NAME__SETTLE_STATUS__TODO = "待结算";          //待结算
    public static final String SETTLE_STATUS__SUCCESS = "结算成功";          //结算成功
    public static final String NAME__SETTLE_STATUS__SUCCESS = "结算成功";          //结算成功
    public static final String SETTLE_STATUS__DOING = "结算中";          //结算中
    public static final String NAME__SETTLE_STATUS__DOING = "结算中";          //结算中
    public static final String SETTLE_STATUS__FAIL = "结算失败";          //结算失败
    public static final String NAME__SETTLE_STATUS__FAIL = "结算失败";          //结算失败
    public static final String SETTLE_STATUS__CANCEL = "作废";          //作废
    public static final String NAME__SETTLE_STATUS__CANCEL = "作废";          //作废

    public static final String TERMINAL__IOS = "1";          //ios
    public static final String NAME__TERMINAL__IOS = "ios";          //ios
    public static final String TERMINAL__ANDROID = "2";          //android
    public static final String NAME__TERMINAL__ANDROID = "android";          //android
    public static final String TERMINAL__PC = "3";          //pc
    public static final String NAME__TERMINAL__PC = "pc";          //pc
    public static final String TERMINAL__OTHER = "4";          //其他
    public static final String NAME__TERMINAL__OTHER = "其他";          //其他

    public static final String FEE_TYPE__FREIGHT = "运费";          //运费
    public static final String NAME__FEE_TYPE__FREIGHT = "运费";          //运费
    public static final String FEE_TYPE__COD = "代收货款";          //代收货款
    public static final String NAME__FEE_TYPE__COD = "代收货款";          //代收货款
    public static final String FEE_TYPE__TRANSIT = "中转费";          //中转费
    public static final String NAME__FEE_TYPE__TRANSIT = "中转费";          //中转费
    public static final String FEE_TYPE__UNLOADING = "到站卸货费";          //到站卸货费
    public static final String NAME__FEE_TYPE__UNLOADING = "到站卸货费";          //到站卸货费
    public static final String FEE_TYPE__INSURANCE = "保险费";          //保险费
    public static final String NAME__FEE_TYPE__INSURANCE = "保险费";          //保险费
    public static final String FEE_TYPE__SOURCE_DELIVERY = "接货费";          //接货费
    public static final String NAME__FEE_TYPE__SOURCE_DELIVERY = "接货费";          //接货费
    public static final String FEE_TYPE__PACKAGE = "二次包装费";          //二次包装费
    public static final String NAME__FEE_TYPE__PACKAGE = "二次包装费";          //二次包装费
    public static final String FEE_TYPE__TRANSFAR = "干线调拨费";          //干线调拨费
    public static final String NAME__FEE_TYPE__TRANSFAR = "干线调拨费";          //干线调拨费
    public static final String FEE_TYPE__DEST_DELIVERY = "送货费";          //送货费
    public static final String NAME__FEE_TYPE__DEST_DELIVERY = "送货费";          //送货费
    public static final String FEE_TYPE__DESTORY = "破损费";          //破损费
    public static final String NAME__FEE_TYPE__DESTORY = "破损费";          //破损费
    public static final String FEE_TYPE__STORAGE = "仓储费";          //仓储费
    public static final String NAME__FEE_TYPE__STORAGE = "仓储费";          //仓储费
    public static final String FEE_TYPE__BUSI_CHARGE_SERVICE = "平台手续费";          //平台手续费
    public static final String NAME__FEE_TYPE__BUSI_CHARGE_SERVICE = "平台手续费";          //平台手续费
    public static final String FEE_TYPE__BACK_COMM = "返佣";          //返佣
    public static final String NAME__FEE_TYPE__BACK_COMM = "返佣";          //返佣
    public static final String FEE_TYPE__FIN_CHARGE_SERVICE = "金融手续费";          //金融手续费
    public static final String NAME__FEE_TYPE__FIN_CHARGE_SERVICE = "金融手续费";          //金融手续费
    public static final String FEE_TYPE__FAXI = "罚息";          //罚息
    public static final String NAME__FEE_TYPE__FAXI = "罚息";          //罚息

    public static final String SETTLE_PERIOD__REALTIME = "REALTIME";          //实时
    public static final String NAME__SETTLE_PERIOD__REALTIME = "实时";          //实时
    public static final String SETTLE_PERIOD__DAILY = "DAILY";          //日结
    public static final String NAME__SETTLE_PERIOD__DAILY = "日结";          //日结
    public static final String SETTLE_PERIOD__PERMONTH = "PERMONTH";          //月结
    public static final String NAME__SETTLE_PERIOD__PERMONTH = "月结";          //月结
    public static final String SETTLE_PERIOD__PERWEEK = "PERWEEK";          //周结
    public static final String NAME__SETTLE_PERIOD__PERWEEK = "周结";          //周结

    public static final String SERIAL_COUNTER_NAME__CENTRAL_ACCOUNT = "centralAccount";          //汇金账号
    public static final String NAME__SERIAL_COUNTER_NAME__CENTRAL_ACCOUNT = "汇金账号";          //汇金账号
    public static final String SERIAL_COUNTER_NAME__PAY_ACCOUNT = "payAccount";          //支付账号
    public static final String NAME__SERIAL_COUNTER_NAME__PAY_ACCOUNT = "支付账号";          //支付账号
    public static final String SERIAL_COUNTER_NAME__TRADE_ACCOUNT = "tradeAccount";          //交易账号
    public static final String NAME__SERIAL_COUNTER_NAME__TRADE_ACCOUNT = "交易账号";          //交易账号
    public static final String SERIAL_COUNTER_NAME__FUND_ACCOUNT = "fundAccount";          //资金账号
    public static final String NAME__SERIAL_COUNTER_NAME__FUND_ACCOUNT = "资金账号";          //资金账号
    public static final String SERIAL_COUNTER_NAME__LF_ACCOUNT = "lfAccount";          //老夫子账号
    public static final String NAME__SERIAL_COUNTER_NAME__LF_ACCOUNT = "老夫子账号";          //老夫子账号

    public static final String ORDER_ID_TYPE__LF56 = "LF56";          //用户账户编号前缀
    public static final String NAME__ORDER_ID_TYPE__LF56 = "用户账户编号前缀";          //用户账户编号前缀
    public static final String ORDER_ID_TYPE__FA56 = "FA56";          //用户产品账户编号前缀
    public static final String NAME__ORDER_ID_TYPE__FA56 = "用户产品账户编号前缀";          //用户产品账户编号前缀
    public static final String ORDER_ID_TYPE__BC = "BC";          //代理商编号前缀
    public static final String NAME__ORDER_ID_TYPE__BC = "代理商编号前缀";          //代理商编号前缀
    public static final String ORDER_ID_TYPE__EX = "EX";          //承运商编号前缀
    public static final String NAME__ORDER_ID_TYPE__EX = "承运商编号前缀";          //承运商编号前缀
    public static final String ORDER_ID_TYPE__PT = "PT";          //产品编号前缀
    public static final String NAME__ORDER_ID_TYPE__PT = "产品编号前缀";          //产品编号前缀
    public static final String ORDER_ID_TYPE__BL = "BL";          //账单编号
    public static final String NAME__ORDER_ID_TYPE__BL = "账单编号";          //账单编号
    public static final String ORDER_ID_TYPE__PR = "PR";          //产品规则编号前缀
    public static final String NAME__ORDER_ID_TYPE__PR = "产品规则编号前缀";          //产品规则编号前缀
    public static final String ORDER_ID_TYPE__LC = "LC";          //借贷合同编号前缀
    public static final String NAME__ORDER_ID_TYPE__LC = "借贷合同编号前缀";          //借贷合同编号前缀
    public static final String ORDER_ID_TYPE__CB = "CB";          //佣金账单编号
    public static final String NAME__ORDER_ID_TYPE__CB = "佣金账单编号";          //佣金账单编号
    public static final String ORDER_ID_TYPE__RP = "RP";          //还款计划编号前缀
    public static final String NAME__ORDER_ID_TYPE__RP = "还款计划编号前缀";          //还款计划编号前缀
    public static final String ORDER_ID_TYPE__OCJ = "OCJ";          //订单变更流水编号
    public static final String NAME__ORDER_ID_TYPE__OCJ = "订单变更流水编号";          //订单变更流水编号
    public static final String ORDER_ID_TYPE__BCJ = "BCJ";          //余额变动流水编号
    public static final String NAME__ORDER_ID_TYPE__BCJ = "余额变动流水编号";          //余额变动流水编号
    public static final String ORDER_ID_TYPE__OD = "OD";          //订单编号
    public static final String NAME__ORDER_ID_TYPE__OD = "订单编号";          //订单编号
    public static final String ORDER_ID_TYPE__LJ = "LJ";          //放款请求流水编号
    public static final String NAME__ORDER_ID_TYPE__LJ = "放款请求流水编号";          //放款请求流水编号
    public static final String ORDER_ID_TYPE__PJ = "PJ";          //付款请求流水编号
    public static final String NAME__ORDER_ID_TYPE__PJ = "付款请求流水编号";          //付款请求流水编号
    public static final String ORDER_ID_TYPE__RJ = "RJ";          //还款请求流水编号
    public static final String NAME__ORDER_ID_TYPE__RJ = "还款请求流水编号";          //还款请求流水编号
    public static final String ORDER_ID_TYPE__CLA = "CLA";          //额度申请流水编号
    public static final String NAME__ORDER_ID_TYPE__CLA = "额度申请流水编号";          //额度申请流水编号
    public static final String ORDER_ID_TYPE__CLJ = "CLJ";          //额度变动流水编号
    public static final String NAME__ORDER_ID_TYPE__CLJ = "额度变动流水编号";          //额度变动流水编号
    public static final String ORDER_ID_TYPE__ALJ = "ALJ";          //合同与可用变动关联编号
    public static final String NAME__ORDER_ID_TYPE__ALJ = "合同与可用变动关联编号";          //合同与可用变动关联编号
    public static final String ORDER_ID_TYPE__MLJ = "MLJ";          //借贷合同变更请求流水编号
    public static final String NAME__ORDER_ID_TYPE__MLJ = "借贷合同变更请求流水编号";          //借贷合同变更请求流水编号

    public static final String BACK_STATUS__TODO = "TODO";          //待回调
    public static final String NAME__BACK_STATUS__TODO = "待回调";          //待回调
    public static final String BACK_STATUS__DONE = "DONE";          //已回调
    public static final String NAME__BACK_STATUS__DONE = "已回调";          //已回调

    public static final String REPAY_MODE__DEMAND = "DEMAND";          //随借随还
    public static final String NAME__REPAY_MODE__DEMAND = "随借随还";          //随借随还
    public static final String REPAY_MODE__MONTH = "MONTH";          //月结
    public static final String NAME__REPAY_MODE__MONTH = "月结";          //月结
    public static final String REPAY_MODE__EVERY = "EVERY";          //按单还
    public static final String NAME__REPAY_MODE__EVERY = "按单还";          //按单还
    public static final String REPAY_MODE__AFTER_FIXED = "AFTER_FIXED";          //放款后固定天数
    public static final String NAME__REPAY_MODE__AFTER_FIXED = "放款后固定天数";          //放款后固定天数

    public static final String SCENE_TYPE__COD = "COD";          //代收货款
    public static final String NAME__SCENE_TYPE__COD = "代收货款";          //代收货款
    public static final String SCENE_TYPE__FREIGHT = "FREIGHT";          //运费
    public static final String NAME__SCENE_TYPE__FREIGHT = "运费";          //运费

    public static final String PRODUCT_TYPE__BAOFU = "BAOFU";          //保付
    public static final String NAME__PRODUCT_TYPE__BAOFU = "保付";          //保付
    public static final String PRODUCT_TYPE__MIAOFU = "MIAOFU";          //秒付
    public static final String NAME__PRODUCT_TYPE__MIAOFU = "秒付";          //秒付
    public static final String PRODUCT_TYPE__JISHIFU = "JISHIFU";          //即时付
    public static final String NAME__PRODUCT_TYPE__JISHIFU = "即时付";          //即时付

    public static final String PRODUCT_ACCOUNT_NAME__BAOFU = "代收货款保付";          //代收货款保付产品账户
    public static final String NAME__PRODUCT_ACCOUNT_NAME__BAOFU = "代收货款保付产品账户";          //代收货款保付产品账户
    public static final String PRODUCT_ACCOUNT_NAME__MIAOFU = "代收货款秒付";          //代收货款秒付产品账户
    public static final String NAME__PRODUCT_ACCOUNT_NAME__MIAOFU = "代收货款秒付产品账户";          //代收货款秒付产品账户
    public static final String PRODUCT_ACCOUNT_NAME__JISHIFU = "代收货款即时付";          //代收货款即时付产品账户
    public static final String NAME__PRODUCT_ACCOUNT_NAME__JISHIFU = "代收货款即时付产品账户";          //代收货款即时付产品账户
    public static final String PRODUCT_ACCOUNT_NAME__BAOFU_SC = "代收货款保付手续费";          //代收货款保付手续费账户
    public static final String NAME__PRODUCT_ACCOUNT_NAME__BAOFU_SC = "代收货款保付手续费账户";          //代收货款保付手续费账户
    public static final String PRODUCT_ACCOUNT_NAME__MIAOFU_SC = "代收货款秒付手续费";          //代收货款秒付手续费账户
    public static final String NAME__PRODUCT_ACCOUNT_NAME__MIAOFU_SC = "代收货款秒付手续费账户";          //代收货款秒付手续费账户
    public static final String PRODUCT_ACCOUNT_NAME__JISHIFU_SC = "代收货款即时付手续费";          //代收货款即时付手续费账户
    public static final String NAME__PRODUCT_ACCOUNT_NAME__JISHIFU_SC = "代收货款即时付手续费账户";          //代收货款即时付手续费账户
    public static final String PRODUCT_ACCOUNT_NAME__BAOFU_BK = "代收货款保付备用";          //代收货款保付备用账户
    public static final String NAME__PRODUCT_ACCOUNT_NAME__BAOFU_BK = "代收货款保付备用账户";          //代收货款保付备用账户
    public static final String PRODUCT_ACCOUNT_NAME__MIAOFU_BK = "代收货款秒付备用";          //代收货款秒付备用账户
    public static final String NAME__PRODUCT_ACCOUNT_NAME__MIAOFU_BK = "代收货款秒付备用账户";          //代收货款秒付备用账户
    public static final String PRODUCT_ACCOUNT_NAME__JISHIFU_BK = "代收货款即时付备用";          //代收货款即时付备用账户
    public static final String NAME__PRODUCT_ACCOUNT_NAME__JISHIFU_BK = "代收货款即时付备用账户";          //代收货款即时付备用账户

    public static final String CALCULATION_SOURCE__TRADE_AMOUNT = "TRADE_AMOUNT";          //基于交易总金额计算
    public static final String NAME__CALCULATION_SOURCE__TRADE_AMOUNT = "基于交易总金额计算";          //基于交易总金额计算
    public static final String CALCULATION_SOURCE__SERVICE_CHARGE = "SERVICE_CHARGE";          //基于手续费计算
    public static final String NAME__CALCULATION_SOURCE__SERVICE_CHARGE = "基于手续费计算";          //基于手续费计算

    public static final String PRODUCT_STATUS__ENABLE = "ENABLE";          //启用
    public static final String NAME__PRODUCT_STATUS__ENABLE = "启用";          //启用
    public static final String PRODUCT_STATUS__DISABLE = "DISABLE";          //停用
    public static final String NAME__PRODUCT_STATUS__DISABLE = "停用";          //停用

    public static final String BUSINESS_PART_CODE__00 = "传化";          //传化
    public static final String NAME__BUSINESS_PART_CODE__00 = "传化";          //传化
    public static final String BUSINESS_PART_CODE__01 = "智慧物流";          //智慧物流
    public static final String NAME__BUSINESS_PART_CODE__01 = "智慧物流";          //智慧物流
    public static final String BUSINESS_PART_CODE__02 = "飞扬";          //飞扬
    public static final String NAME__BUSINESS_PART_CODE__02 = "飞扬";          //飞扬
    public static final String BUSINESS_PART_CODE__03 = "天龙";          //天龙
    public static final String NAME__BUSINESS_PART_CODE__03 = "天龙";          //天龙
    public static final String BUSINESS_PART_CODE__04 = "时创";          //时创
    public static final String NAME__BUSINESS_PART_CODE__04 = "时创";          //时创
    public static final String BUSINESS_PART_CODE__05 = "硕诺";          //硕诺
    public static final String NAME__BUSINESS_PART_CODE__05 = "硕诺";          //硕诺
    public static final String BUSINESS_PART_CODE__06 = "车满满";          //车满满
    public static final String NAME__BUSINESS_PART_CODE__06 = "车满满";          //车满满
    public static final String BUSINESS_PART_CODE__07 = "蓝桥";          //蓝桥
    public static final String NAME__BUSINESS_PART_CODE__07 = "蓝桥";          //蓝桥

    public static final String BANK_ACCOUNT_TYPE__PUBLIC = "PUB";          //对公
    public static final String NAME__BANK_ACCOUNT_TYPE__PUBLIC = "对公";          //对公
    public static final String BANK_ACCOUNT_TYPE__PRIVATE = "PRI";          //对私
    public static final String NAME__BANK_ACCOUNT_TYPE__PRIVATE = "对私";          //对私

    public static final String OUT_PARTY_TYPE__USER_ID = "USER_ID";          //会员号
    public static final String NAME__OUT_PARTY_TYPE__USER_ID = "会员号";          //会员号
    public static final String OUT_PARTY_TYPE__BANK_NO_PRI = "BANK_NO_PRI";          //个人银行卡
    public static final String NAME__OUT_PARTY_TYPE__BANK_NO_PRI = "个人银行卡";          //个人银行卡
    public static final String OUT_PARTY_TYPE__BANK_NO_PUB = "BANK_NO_PUB";          //对公银行卡
    public static final String NAME__OUT_PARTY_TYPE__BANK_NO_PUB = "对公银行卡";          //对公银行卡
    public static final String OUT_PARTY_TYPE__MOBILE = "MOBILE";          //手机号
    public static final String NAME__OUT_PARTY_TYPE__MOBILE = "手机号";          //手机号

    public static final String REPAY_STATUS__REPAY_STATUS_TODO = "1";          //未还款
    public static final String NAME__REPAY_STATUS__REPAY_STATUS_TODO = "未还款";          //未还款
    public static final String REPAY_STATUS__REPAY_STATUS_DOING = "2";          //还款中
    public static final String NAME__REPAY_STATUS__REPAY_STATUS_DOING = "还款中";          //还款中
    public static final String REPAY_STATUS__REPAY_STATUS_SUCCESS = "3";          //还款成功
    public static final String NAME__REPAY_STATUS__REPAY_STATUS_SUCCESS = "还款成功";          //还款成功
    public static final String REPAY_STATUS__REPAY_STATUS_FAILED = "4";          //还款失败
    public static final String NAME__REPAY_STATUS__REPAY_STATUS_FAILED = "还款失败";          //还款失败
    public static final String REPAY_STATUS__REPAY_STATUS_OFFLINE = "5";          //线下还款
    public static final String NAME__REPAY_STATUS__REPAY_STATUS_OFFLINE = "线下还款";          //线下还款

    public static final String LF_ACCOUNT_ROLE__CARRIER = "carrier";          //承运商
    public static final String NAME__LF_ACCOUNT_ROLE__CARRIER = "承运商";          //承运商
    public static final String LF_ACCOUNT_ROLE__CONSIGNOR = "consignor";          //发货人
    public static final String NAME__LF_ACCOUNT_ROLE__CONSIGNOR = "发货人";          //发货人
    public static final String LF_ACCOUNT_ROLE__CONSIGNEE = "consignee";          //收货人
    public static final String NAME__LF_ACCOUNT_ROLE__CONSIGNEE = "收货人";          //收货人
    public static final String LF_ACCOUNT_ROLE__PAYEE = "payee";          //收款人
    public static final String NAME__LF_ACCOUNT_ROLE__PAYEE = "收款人";          //收款人

    public static final String ORG_BUSINESSNUM_TYPE__CREDIT_CHANGE = "CREDIT_CHANGE";          //授信额度变更
    public static final String NAME__ORG_BUSINESSNUM_TYPE__CREDIT_CHANGE = "授信额度变更";          //授信额度变更
    public static final String ORG_BUSINESSNUM_TYPE__CONSUME = "CONSUME";          //消费
    public static final String NAME__ORG_BUSINESSNUM_TYPE__CONSUME = "消费";          //消费
    public static final String ORG_BUSINESSNUM_TYPE__REPAY = "REPAY";          //还款
    public static final String NAME__ORG_BUSINESSNUM_TYPE__REPAY = "还款";          //还款
    public static final String ORG_BUSINESSNUM_TYPE__REVERSE = "REVERSE";          //冲正
    public static final String NAME__ORG_BUSINESSNUM_TYPE__REVERSE = "冲正";          //冲正

    public static final String ORG_BUSINESSJOURNUM_TYPE__FIN_ORDER_CHANGE_JOUR = "1";          //金融订单变更流水
    public static final String NAME__ORG_BUSINESSJOURNUM_TYPE__FIN_ORDER_CHANGE_JOUR = "金融订单变更流水";          //金融订单变更流水

    public static final String PRICE_TYPE__FREIGHT = "1";          //运费
    public static final String NAME__PRICE_TYPE__FREIGHT = "运费";          //运费
    public static final String PRICE_TYPE__VEHICLE_PRICE = "2";          //整车价
    public static final String NAME__PRICE_TYPE__VEHICLE_PRICE = "整车价";          //整车价

    public static final String POUNDAGE_CALC_TYPE__RATIO = "RATIO";          //按比例
    public static final String NAME__POUNDAGE_CALC_TYPE__RATIO = "按比例";          //按比例
    public static final String POUNDAGE_CALC_TYPE__DAILY_RATE = "DAILY_RATE";          //按日利率
    public static final String NAME__POUNDAGE_CALC_TYPE__DAILY_RATE = "按日利率";          //按日利率
    public static final String POUNDAGE_CALC_TYPE__ = "";          //
    public static final String NAME__POUNDAGE_CALC_TYPE__ = "";          //

    public static final String ACCOUNT_PERIOD_CALC_TYPE__REALTIME = "REALTIME";          //实时
    public static final String NAME__ACCOUNT_PERIOD_CALC_TYPE__REALTIME = "实时";          //实时
    public static final String ACCOUNT_PERIOD_CALC_TYPE__DAILY = "DAILY";          //日结
    public static final String NAME__ACCOUNT_PERIOD_CALC_TYPE__DAILY = "日结";          //日结
    public static final String ACCOUNT_PERIOD_CALC_TYPE__PERMONTH = "PERMONTH";          //月结
    public static final String NAME__ACCOUNT_PERIOD_CALC_TYPE__PERMONTH = "月结";          //月结
    public static final String ACCOUNT_PERIOD_CALC_TYPE__PERWEEK = "PERWEEK";          //周结
    public static final String NAME__ACCOUNT_PERIOD_CALC_TYPE__PERWEEK = "周结";          //周结

    public static final String LOAN_CONTRACT_STATUS__INIT = "1";          //初始化
    public static final String NAME__LOAN_CONTRACT_STATUS__INIT = "初始化";          //初始化
    public static final String LOAN_CONTRACT_STATUS__PENDING = "2";          //待放款
    public static final String NAME__LOAN_CONTRACT_STATUS__PENDING = "待放款";          //待放款
    public static final String LOAN_CONTRACT_STATUS__CANCEL = "3";          //已作废
    public static final String NAME__LOAN_CONTRACT_STATUS__CANCEL = "已作废";          //已作废
    public static final String LOAN_CONTRACT_STATUS__LOANED = "4";          //放款成功
    public static final String NAME__LOAN_CONTRACT_STATUS__LOANED = "放款成功";          //放款成功
    public static final String LOAN_CONTRACT_STATUS__FAILED = "5";          //放款失败
    public static final String NAME__LOAN_CONTRACT_STATUS__FAILED = "放款失败";          //放款失败

    public static final String LOAN_STATUS__INIT = "1";          //待放款
    public static final String NAME__LOAN_STATUS__INIT = "待放款";          //待放款
    public static final String LOAN_STATUS__ING = "2";          //放款中
    public static final String NAME__LOAN_STATUS__ING = "放款中";          //放款中
    public static final String LOAN_STATUS__SECCESS = "3";          //放款成功
    public static final String NAME__LOAN_STATUS__SECCESS = "放款成功";          //放款成功
    public static final String LOAN_STATUS__FAILED = "4";          //放款失败
    public static final String NAME__LOAN_STATUS__FAILED = "放款失败";          //放款失败

    public static final String REPAY_JOUR_STATUS__APPLIED = "1";          //已申请
    public static final String NAME__REPAY_JOUR_STATUS__APPLIED = "已申请";          //已申请
    public static final String REPAY_JOUR_STATUS__ING = "2";          //还款中
    public static final String NAME__REPAY_JOUR_STATUS__ING = "还款中";          //还款中
    public static final String REPAY_JOUR_STATUS__SUCCESS = "3";          //还款成功
    public static final String NAME__REPAY_JOUR_STATUS__SUCCESS = "还款成功";          //还款成功
    public static final String REPAY_JOUR_STATUS__FAILED = "4";          //还款失败
    public static final String NAME__REPAY_JOUR_STATUS__FAILED = "还款失败";          //还款失败

    public static final String STATEMENT_STATUS__INIT = "1";          //已生成
    public static final String NAME__STATEMENT_STATUS__INIT = "已生成";          //已生成
    public static final String STATEMENT_STATUS__ING = "2";          //出账中
    public static final String NAME__STATEMENT_STATUS__ING = "出账中";          //出账中
    public static final String STATEMENT_STATUS__SUCCESS = "3";          //出账成功
    public static final String NAME__STATEMENT_STATUS__SUCCESS = "出账成功";          //出账成功

    public static final String ACCOUNT_TYPE__BIZ = "BIZ";          //业务账户
    public static final String NAME__ACCOUNT_TYPE__BIZ = "业务账户";          //业务账户
    public static final String ACCOUNT_TYPE__FEE = "FEE";          //手续费账户
    public static final String NAME__ACCOUNT_TYPE__FEE = "手续费账户";          //手续费账户
    public static final String ACCOUNT_TYPE__BACKUP = "BACKUP";          //备用账户
    public static final String NAME__ACCOUNT_TYPE__BACKUP = "备用账户";          //备用账户

    public static final String FILTER_TYPE__ALL = "ALL";          //不过滤
    public static final String NAME__FILTER_TYPE__ALL = "不过滤";          //不过滤
    public static final String FILTER_TYPE__ONLY_WHITE = "ONLY_WHITE";          //仅白名单可用
    public static final String NAME__FILTER_TYPE__ONLY_WHITE = "仅白名单可用";          //仅白名单可用
    public static final String FILTER_TYPE__EXCEPT_BLACK = "EXCEPT_BLACK";          //除黑名单可用
    public static final String NAME__FILTER_TYPE__EXCEPT_BLACK = "除黑名单可用";          //除黑名单可用

    public static final String MODIFY_LOAN_TYPE__AMOUNT = "1";          //合同金额有变更
    public static final String NAME__MODIFY_LOAN_TYPE__AMOUNT = "合同金额有变更";          //合同金额有变更
    public static final String MODIFY_LOAN_TYPE__NONE_AMOUNT = "2";          //合同金额无变更
    public static final String NAME__MODIFY_LOAN_TYPE__NONE_AMOUNT = "合同金额无变更";          //合同金额无变更

    public static final String MODIFY_LOAN_STATUS__CHANGING = "0";          //变更中
    public static final String NAME__MODIFY_LOAN_STATUS__CHANGING = "变更中";          //变更中
    public static final String MODIFY_LOAN_STATUS__SUCCESS = "1";          //变更成功
    public static final String NAME__MODIFY_LOAN_STATUS__SUCCESS = "变更成功";          //变更成功
    public static final String MODIFY_LOAN_STATUS__FAILED = "2";          //变更失败
    public static final String NAME__MODIFY_LOAN_STATUS__FAILED = "变更失败";          //变更失败
    public static final String MODIFY_LOAN_STATUS__REVERSED = "3";          //已反冲
    public static final String NAME__MODIFY_LOAN_STATUS__REVERSED = "已反冲";          //已反冲

    public static final String MSG_TYPE__PRODUCE = "P";          //生产
    public static final String NAME__MSG_TYPE__PRODUCE = "生产";          //生产
    public static final String MSG_TYPE__CONSUME = "C";          //消费
    public static final String NAME__MSG_TYPE__CONSUME = "消费";          //消费
    public static final String MSG_TYPE__RPC = "R";          //远程调用
    public static final String NAME__MSG_TYPE__RPC = "远程调用";          //远程调用

    public static final String MSG_PROCESS_STATUS__CONSUMING = "1";          //消费中
    public static final String NAME__MSG_PROCESS_STATUS__CONSUMING = "消费中";          //消费中
    public static final String MSG_PROCESS_STATUS__CONSUME_SUCCESS = "2";          //消费成功
    public static final String NAME__MSG_PROCESS_STATUS__CONSUME_SUCCESS = "消费成功";          //消费成功
    public static final String MSG_PROCESS_STATUS__CONSUME_FAIL = "3";          //消费失败
    public static final String NAME__MSG_PROCESS_STATUS__CONSUME_FAIL = "消费失败";          //消费失败
    public static final String MSG_PROCESS_STATUS__PRODUCING = "4";          //处理中
    public static final String NAME__MSG_PROCESS_STATUS__PRODUCING = "处理中";          //处理中
    public static final String MSG_PROCESS_STATUS__PRODUCE_SUCCESS = "5";          //处理成功
    public static final String NAME__MSG_PROCESS_STATUS__PRODUCE_SUCCESS = "处理成功";          //处理成功
    public static final String MSG_PROCESS_STATUS__CANCELLING = "6";          //撤销中
    public static final String NAME__MSG_PROCESS_STATUS__CANCELLING = "撤销中";          //撤销中
    public static final String MSG_PROCESS_STATUS__CANCELED = "7";          //撤销成功
    public static final String NAME__MSG_PROCESS_STATUS__CANCELED = "撤销成功";          //撤销成功

    public static final String AVAILABLE_LIMIT_JOUR_STATUS__SUCCESS = "1";          //处理成功
    public static final String NAME__AVAILABLE_LIMIT_JOUR_STATUS__SUCCESS = "处理成功";          //处理成功
    public static final String AVAILABLE_LIMIT_JOUR_STATUS__REVERSED = "2";          //已反冲
    public static final String NAME__AVAILABLE_LIMIT_JOUR_STATUS__REVERSED = "已反冲";          //已反冲
    public static final String AVAILABLE_LIMIT_JOUR_STATUS__FAILED = "3";          //处理失败
    public static final String NAME__AVAILABLE_LIMIT_JOUR_STATUS__FAILED = "处理失败";          //处理失败
    public static final String AVAILABLE_LIMIT_JOUR_STATUS__DEALING = "4";          //处理中
    public static final String NAME__AVAILABLE_LIMIT_JOUR_STATUS__DEALING = "处理中";          //处理中

    public static final String AVAILABLE_LIMIT_BIZ_TYPE__ADD = "1";          //增加
    public static final String NAME__AVAILABLE_LIMIT_BIZ_TYPE__ADD = "增加";          //增加
    public static final String AVAILABLE_LIMIT_BIZ_TYPE__SUB = "2";          //减少
    public static final String NAME__AVAILABLE_LIMIT_BIZ_TYPE__SUB = "减少";          //减少
    public static final String AVAILABLE_LIMIT_BIZ_TYPE__REVERSE = "3";          //反冲
    public static final String NAME__AVAILABLE_LIMIT_BIZ_TYPE__REVERSE = "反冲";          //反冲

    public static final String EXTERNAL_TRADE_TYPE__LOGISTICS_ORDER = "1";          //物流订单
    public static final String NAME__EXTERNAL_TRADE_TYPE__LOGISTICS_ORDER = "物流订单";          //物流订单

    public static final String BIZ_ACTION__ORDER = "ORDER";          //下单
    public static final String NAME__BIZ_ACTION__ORDER = "下单";          //下单
    public static final String BIZ_ACTION__ACCEPT = "ACCEPT";          //承运商接单
    public static final String NAME__BIZ_ACTION__ACCEPT = "承运商接单";          //承运商接单
    public static final String BIZ_ACTION__BEGIN_TRANSPORT = "BEGIN_TRANSPORT";          //开始运输
    public static final String NAME__BIZ_ACTION__BEGIN_TRANSPORT = "开始运输";          //开始运输
    public static final String BIZ_ACTION__MODIFY = "MODIFY";          //订单修改
    public static final String NAME__BIZ_ACTION__MODIFY = "订单修改";          //订单修改
    public static final String BIZ_ACTION__CANCEL = "CANCEL";          //订单作废
    public static final String NAME__BIZ_ACTION__CANCEL = "订单作废";          //订单作废
    public static final String BIZ_ACTION__CONFIRM = "CONFIRM";          //承运商确认收款
    public static final String NAME__BIZ_ACTION__CONFIRM = "承运商确认收款";          //承运商确认收款

    public static final String BONUS_OBJ_TYPE__EXPRESS = "express";          //承运商
    public static final String NAME__BONUS_OBJ_TYPE__EXPRESS = "承运商";          //承运商
    public static final String BONUS_OBJ_TYPE__TMS = "tms";          //TMS
    public static final String NAME__BONUS_OBJ_TYPE__TMS = "TMS";          //TMS
    public static final String BONUS_OBJ_TYPE__AGENCY = "agency";          //代理商
    public static final String NAME__BONUS_OBJ_TYPE__AGENCY = "代理商";          //代理商

    public static final String FIN_ORDER_CHANGE_RESULT__CHANGE_SUCCESS = "0";          //改单成功
    public static final String NAME__FIN_ORDER_CHANGE_RESULT__CHANGE_SUCCESS = "改单成功";          //改单成功
    public static final String FIN_ORDER_CHANGE_RESULT__FAIL = "1";          //改单失败
    public static final String NAME__FIN_ORDER_CHANGE_RESULT__FAIL = "改单失败";          //改单失败
    public static final String FIN_ORDER_CHANGE_RESULT__LOAN_SUCCESS = "2";          //放款成功
    public static final String NAME__FIN_ORDER_CHANGE_RESULT__LOAN_SUCCESS = "放款成功";          //放款成功

    public static final String DISTRICT_LEVEL__ONE = "1";          //一级
    public static final String NAME__DISTRICT_LEVEL__ONE = "一级";          //一级
    public static final String DISTRICT_LEVEL__TWO = "2";          //二级
    public static final String NAME__DISTRICT_LEVEL__TWO = "二级";          //二级
    public static final String DISTRICT_LEVEL__THREE = "3";          //三级
    public static final String NAME__DISTRICT_LEVEL__THREE = "三级";          //三级

    public static final String EXPRESS_TAKEN__YES = "1";          //已接单
    public static final String NAME__EXPRESS_TAKEN__YES = "已接单";          //已接单
    public static final String EXPRESS_TAKEN__NO = "2";          //未接单
    public static final String NAME__EXPRESS_TAKEN__NO = "未接单";          //未接单

    public static final String ORDER_LOAN_REPAY_STATUS__LOAN_SUCCESS = "1";          //放款成功
    public static final String NAME__ORDER_LOAN_REPAY_STATUS__LOAN_SUCCESS = "放款成功";          //放款成功
    public static final String ORDER_LOAN_REPAY_STATUS__REPAY_SUCCESS = "2";          //还款成功
    public static final String NAME__ORDER_LOAN_REPAY_STATUS__REPAY_SUCCESS = "还款成功";          //还款成功

    static final Map<String,HashMap<String,DictEntry>> dicts = new HashMap<String,HashMap<String,DictEntry>>();
    static {
    	// dictName		subDictCnst	subDictValue
    	Field[] fields = DictCons.class.getFields();
        for( Field field : fields ){
        if (!field.getName().startsWith("NAME__")) {
            try {
            	dicts.put(DictUtils.underline2Camel(field.getName().substring(0,field.getName().indexOf("__")).toLowerCase(), true),new HashMap<String,DictEntry>());
    		} catch (IllegalArgumentException e) {
    			e.printStackTrace();
    		}
        }
        }
    
        for( Field field : fields ){
        if (!field.getName().startsWith("NAME__")) {
            try {
            	String dictEnName = DictUtils.underline2Camel(field.getName().substring(0,field.getName().indexOf("__")).toLowerCase(),true);
            	String subDictCnst = field.getName().substring(field.getName().indexOf("__")+2);
            	Field fieldName = DictCons.class.getField("NAME__" + field.getName());
            	dicts.get(dictEnName).put(field.get(DictCons.class).toString(),new DictEntry(field.get(DictCons.class).toString(),fieldName.get(DictCons.class).toString()));
    		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
    			e.printStackTrace();
    		}
        }
        }
    }
        public static void main(String[] args) {
        	for (Entry<String, HashMap<String, DictEntry>> entry : dicts.entrySet()) {
        	System.out.println("Key = " + entry.getKey() + ", Value = " + JSON.toJSONString(entry.getValue()));
        }
    } 
 
} 
