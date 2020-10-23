package com.yhml.tools.money;

import com.yhml.tools.constants.AccountEnum;
import com.yhml.tools.constants.CatalogEnum;
import com.yhml.tools.constants.TradeTypeEnum;
import com.yhml.tools.money.bill.AlipayBill;
import com.yhml.tools.money.bill.MoneyPro;
import com.yhml.tools.util.CsvUtils;
import org.junit.Test;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jfeng
 * @date 2020/8/25
 */
@Slf4j
public class AlipayHander implements IMoneyHandler {

    @Test
    public void process() {
        File file = CsvUtils.getCsvBill("alipay_record");
        List<MoneyPro> data = parseBill(file);
        CsvUtils.writer(data, "data.csv");
        log.info("MoneyPro账单笔数:{}", data.size());
    }

    private List<MoneyPro> parseBill(File file) {
        List<AlipayBill> list = read(file);
        List<MoneyPro> data = new ArrayList<>();
        int yuebaoCount = 0;
        int failCount = 0;
        for (AlipayBill model : list) {
            // 过滤余额宝每日收益, 月底统一结算
            if (model.isYueBao()) {
                yuebaoCount++;
                continue;
            }
            // 过滤不成功交易笔数
            if (model.isfailTrade()) {
                failCount++;
                continue;
            }

            try {
                data.add(toMoneyPro(model));
            } catch (Exception e) {
                log.error("转换MoneyPro对象出错:{}", model, e);
            }
        }
        log.info("余额宝收益笔数:{}", yuebaoCount);
        log.info("未成功交易笔数:{}", failCount);
        return data;
    }

    private MoneyPro toMoneyPro(AlipayBill model) {
        MoneyPro pro = new MoneyPro();
        pro.setAccount(AccountEnum.CMB1799.getProAccount());
        pro.setTradeTime(model.getTradeTime());
        pro.setAmount(model.getAmount());
        pro.setTradeType(TradeTypeEnum.isIncome(model.getTradeType()) ? TradeTypeEnum.INCOME.getType() : TradeTypeEnum.CONSUME.getType());

        if (model.isHuaBei()) {
            pro.setAccount(AccountEnum.CMB2071.getProAccount());
            pro.setToAccount(AccountEnum.HUABEI.getProAccount());
            pro.setDesc("花呗还款");
        } else if (model.isTransfer2YuerBao()) {
            pro.setAccount(AccountEnum.CMB2071.getProAccount());
            pro.setToAccount(AccountEnum.YUEBAO.getProAccount());
        } else if (model.isRefundCMB()) {
            pro.setAccount(AccountEnum.CMB2071.getProAccount());
            pro.setToAccount(AccountEnum.CMB1799.getProAccount());
            pro.setDesc("信用卡还款");
        } else {
            parseCatalog(model, pro);
        }

        // 处理基金买入卖出
        if (model.isFund()) {
            // 基金名称
            pro.setDesc(model.getProductName().split("-")[1]);
            if (TradeTypeEnum.isIncome(model.getTradeType())) {
                pro.setCatalog(CatalogEnum.FUND.getCatalog());
            } else {
                pro.setAccount(AccountEnum.YUEBAO.getProAccount());
                pro.setToAccount(pro.getDesc());
            }
        }

        // 余额宝收益
        if (model.isYueBao()) {
            pro.setAccount(AccountEnum.YUEBAO.getProAccount());
            pro.setCatalog(CatalogEnum.LC.getCatalog());
        }

        return pro;
    }

    private void parseCatalog(AlipayBill model, MoneyPro pro) {
        CatalogEnum catalog = CatalogEnum.getProCatalog(model.getProductName(), model.getTradeName());

        if (catalog == null) {
            log.info("分类不存在, 默认分类:购物, 详情:{}", model);
            catalog = CatalogEnum.GW;
            pro.setDesc(model.getTradeName());
        }

        if (catalog.equals(CatalogEnum.GW) || catalog.equals(CatalogEnum.ZC)) {
            int hour = DateUtil.parse(model.getTradeTime()).getField(DateField.HOUR_OF_DAY);
            if (hour >= 7 && hour <= 9) {
                catalog = CatalogEnum.ZC;
                pro.setDesc("早饭");
            } else if (hour >= 11 && hour <= 13) {
                catalog = CatalogEnum.ZC;
                pro.setDesc("午饭");
            } else if (hour >= 17 && hour <= 19) {
                catalog = CatalogEnum.ZC;
                pro.setDesc("晚饭");
            }
        }
        pro.setCatalog(catalog.getCatalog());
        pro.setLable(StrUtil.isBlank(catalog.getLabel()) ? "个人" : catalog.getLabel());

        if (catalog.equals(CatalogEnum.LF)) {
            pro.setAccount(AccountEnum.HUABEI.getProAccount());
        }
        if (model.getProductName().contains("超市")) {
            pro.setDesc("超市");
        }
    }

    private static List<AlipayBill> read(File file) {
        CsvData data = CsvUtils.getReader().read(file, CharsetUtil.CHARSET_GBK);
        List<String> header = new ArrayList<>();
        List<AlipayBill> result = new ArrayList<>();
        for (CsvRow csvRow : data.getRows()) {
            if (csvRow.getRawList().size() == 1) {
                log.info("{}", csvRow.getRawList());
                continue;
            }
            if (csvRow.getOriginalLineNumber() == 5) {
                header = csvRow.getRawList().stream().map(s -> s.trim()).collect(Collectors.toList());
                log.info("开始解析 行号={}", csvRow.getOriginalLineNumber() + 1);
                continue;
            }
            if (csvRow.getRawList().size() == header.size()) {
                result.add(CsvUtils.toBean(header, csvRow, AlipayBill.class));
            }
        }
        return result;
    }
}
