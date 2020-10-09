package com.yhml.tools.money;

import com.yhml.tools.constants.CatalogEnum;
import com.yhml.tools.constants.TradeTypeEnum;
import com.yhml.tools.money.bill.MoneyPro;
import com.yhml.tools.money.bill.MoneyWiz;
import com.yhml.tools.util.CsvUtils;
import org.junit.Test;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.io.File;
import java.util.*;

import lombok.extern.slf4j.Slf4j;

/**
 * Moneypro 数据转 moneywiz
 *
 * @date 2020/5/18
 */
@Slf4j
public class MoneyProHandler implements IMoneyHandler {

    public static MoneyProHandler INSTANCE = new MoneyProHandler();
    /**
     * Pro账单转Wiz
     */
    @Test
    public void process() {
        File file = CsvUtils.getCsvBill("Money Pro");
        List<MoneyWiz> data = prarseBill(file);
        CsvUtils.writer(data, "MoneyWiz.csv");
        log.info("MoneyPor转MoneyWiz账单, 完成:{}笔", data.size());
    }

    private List<MoneyWiz> prarseBill(File file) {
        List<MoneyPro> list = CsvUtils.read(file, MoneyPro.class);
        List<MoneyWiz> result = new ArrayList<>(list.size());
        log.debug(JSONUtil.toJsonPrettyStr(list));
        int index = 1;
        for (MoneyPro pro : list) {
            index++;
            if (TradeTypeEnum.notContains(pro.getTradeType())) {
                log.info("过滤记录, 索引:{}, 交易:{}", index, pro.getTradeType());
                continue;
            }

            // 资产买入卖出 如果没有交易对象
            if ((pro.isAssetsBuy() || pro.isAssetsSold()) && StrUtil.isBlank(pro.getDesc())) {
                log.info("资产买卖, 转入账户为空, 序号:{}, 内容:{}", index, pro);
            }

            try {
                result.add(toMoneyWiz(pro));
            } catch (Exception e) {
                log.error("转换MoneyWiz对象出错:{}", pro, e);
            }
        }

        return result;
    }

    private MoneyWiz toMoneyWiz(MoneyPro pro) {
        String amount = pro.getAmount();
        String catalog = parseCatalog(pro.getCatalog());
        MoneyWiz bean = new MoneyWiz();
        bean.setAmount(amount);
        bean.setAccount(pro.getAccount());
        bean.setDesc(pro.getDesc());
        bean.setBusiness(pro.getBusiness());
        bean.setCheckNo(pro.getCheckNo());
        bean.setCatalog(catalog);
        DateTime dateTime = DateUtil.parse(pro.getTradeTime().trim(), "yyyy年M月dd日 HH:mm");
        bean.setTradeTime(dateTime.toString("yyyy/dd/MM"));
        bean.setTime(dateTime.toString("HH:mm"));

        if (StrUtil.isNotBlank(pro.getLable())) {
            bean.setLable(pro.getLable());
        }

        // 消费
        if (pro.isConsume()) {
            bean.setAmount("-" + amount);
            if (StrUtil.isBlank(pro.getDesc()) && catalog.contains(">")) {
                bean.setDesc(catalog.split(">")[1].trim());
            }
        }

        // 转账
        if (pro.isTransfer()) {
            bean.setAmount("-" + amount);
            bean.setToAccount(pro.getToAccount());
            if (StrUtil.isBlank(pro.getDesc())) {
                bean.setDesc(StrUtil.format("转入{}", pro.getToAccount()));
            }
            // bean.setCharge(amount);
            // bean.setDesc(" " + StrUtil.format("{} 和{}之间的转账", bean.getAccount(), copy.getTransfer()));
        }

        // 资产买入
        // 代理人 就是转入的账户
        // account 转入 投资账户
        if (pro.isAssetsBuy()) {
            bean.setAmount("-" + amount);
            bean.setToAccount(pro.getDesc());
            bean.setDesc(TradeTypeEnum.ASSETS_BUY.getType());
            bean.setCatalog("");
            bean.setBusiness("");
        }

        // 资产卖出
        // account 是被转入的对象, 金额为真
        // 代理人就是转出的账户
        // 投资账户 转入 account,
        if (pro.isAssetsSold()) {
            // 转账没有分类
            bean.setCatalog("");
            bean.setBusiness("");
            bean.setAmount(amount);
            bean.setToAccount(pro.getDesc());
            bean.setDesc(TradeTypeEnum.ASSETS_SOLD.getType());
            if (pro.getCatalog().equals(CatalogEnum.GJJ.getCatalog())) {
                bean.setToAccount(CatalogEnum.GJJ.getCatalog());
                bean.setDesc("转入");
            }
        }

        // 结余调整
        if (pro.isBalance()) {
            bean.setAmount(amount);
            bean.setCatalog(CatalogEnum.getWizCatalog(bean.getAccount()));
            bean.setDesc(StrUtil.isBlank(pro.getDesc()) ? "新余额" : pro.getDesc());
            if (bean.getCatalog().equals(CatalogEnum.FUND.getType())) {
                bean.setDesc("收益");
            }
        }

        return bean;
    }

    /**
     * 删除重复字符串
     */
    private static String parseCatalog(String str) {
        int count = StrUtil.count(str, ":");
        if (count > 1) {
            String[] split = str.split("");
            Set<String> set = new LinkedHashSet<>(Arrays.asList(split));
            str = CollectionUtil.join(set, "");
        }
        return str.replace(":", " > ");
    }
}

