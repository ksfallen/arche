package com.yhml.tools.money;

import com.yhml.tools.constants.AccountEnum;
import com.yhml.tools.constants.CatalogEnum;
import com.yhml.tools.constants.TradeTypeEnum;
import com.yhml.tools.model.CsvModel;
import com.yhml.tools.money.bill.AlipayBill;
import com.yhml.tools.money.bill.MoneyPro;
import com.yhml.tools.util.CsvUtil;
import org.junit.Test;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
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
    public void porcess() {
        File file = CsvUtil.getCsvBill("alipay_record");
        List<AlipayBill> list = read(file, AlipayBill.class);

        List<MoneyPro> data = new ArrayList<>();

        for (AlipayBill model : list) {
            try {
                data.add(processBill(model));
            } catch (Exception e) {
                log.error("转换MoneyPro对象出错:{}", model, e);
            }
        }

        CsvUtil.writer(data, "data.csv");
        log.info("支付宝转MoneyPor账单, 完成:{}笔", data.size());
    }

    private MoneyPro processBill(AlipayBill model) {
        MoneyPro pro = new MoneyPro();
        pro.setAccount(getAccount(model));
        pro.setTradeTime(model.getTradeTime());
        pro.setAmount(model.getAmount());
        pro.setCatalog(getCatalog(model, pro));
        pro.setTradeType(TradeTypeEnum.isIncome(model.getTradeType()) ? TradeTypeEnum.INCOME.getType() : TradeTypeEnum.CONSUME.getType());
        pro.setLable("个人");

        // 处理基金买入卖出
        if (model.isFund()) {
            pro.setDesc(model.getProductName().split("-")[1]); // 基金名称
            if (TradeTypeEnum.isIncome(model.getTradeType())) {
                pro.setCatalog(CatalogEnum.FUND.getAccount());
            }
        }

        // 余额宝
        if (model.isYueBao()) {
            pro.setAccount(AccountEnum.YUEBAO.getProAccount());
            pro.setCatalog(CatalogEnum.LX.getCatalog());
            pro.setDesc("收益");
        }

        if (CatalogEnum.ZC.getCatalog().equals(pro.getCatalog()) || CatalogEnum.GT.getCatalog().equals(pro.getCatalog())
                || CatalogEnum.DC.getCatalog().equals(pro.getCatalog())) {
            pro.setLable("杭州");
        }

        return pro;
    }

    private String getCatalog(AlipayBill model, MoneyPro pro) {
        String catalog = CatalogEnum.getCatalog(model.getProductName());
        if (StrUtil.isBlank(catalog)) {
            catalog = CatalogEnum.getCatalog(model.getTradeName());
        }

        if (StrUtil.isBlank(catalog)) {
            catalog = CatalogEnum.GW.getCatalog();
        }

        int hour = DateUtil.parse(model.getTradeTime()).getField(DateField.HOUR);

        // 中午
        if ((hour >= 11 && hour <= 13) && catalog.equals(CatalogEnum.GW.getCatalog())) {
            catalog = CatalogEnum.ZC.getCatalog();
            pro.setDesc("午饭");
        }

        // 傍晚
        if ((hour >= 17 && hour <= 19) && catalog.equals(CatalogEnum.GW.getCatalog())) {
            catalog = CatalogEnum.ZC.getCatalog();
            pro.setDesc("晚饭");
        }

        return catalog;
    }

    private String getAccount(AlipayBill model) {
        if (model.isFund()) {
            return AccountEnum.YUEBAO.getProAccount();
        }
        // String account = AlipayLogin.getBillDetail(model.getTradeId());
        // String proAccount = AccountEnum.getProAccount(account);
        // if (StrUtil.isNotBlank(proAccount)) {
        //     return proAccount;
        // }
        return AccountEnum.CMB1799.getProAccount();
    }

    private static <T extends CsvModel> List<T> read(File file, Class<T> clazz) {
        CsvReader reader = CsvUtil.getReader();
        CsvData data = reader.read(file, CharsetUtil.CHARSET_GBK);
        List<String> header = new ArrayList<>();
        List<T> result = new ArrayList<>();

        for (CsvRow csvRow : data.getRows()) {
            if (csvRow.getRawList().size() <= 5) {
                log.info("{}", csvRow.getRawList());
                continue;
            }
            if (csvRow.getOriginalLineNumber() == 5) {
                header = csvRow.getRawList().stream().map(s -> s.trim()).collect(Collectors.toList());
                // log.info("开始解析 行号={}", csvRow.getOriginalLineNumber() + 1);
                continue;
            }
            result.add(CsvUtil.toBean(header, csvRow, clazz));
        }
        return result;
    }
}
