package com.yhml.tools.money;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import com.yhml.tools.model.CsvModel;
import com.yhml.tools.model.ToolRunner;
import org.junit.Test;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.csv.*;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Moneypro 数据转 moneywiz
 *
 * @date 2020/5/18
 */
@Slf4j
public class MoneyRunner implements ToolRunner {
    public static final String path = "/Users/Jfeng/Downloads";

    private static int totalSize = 0;

    private static File getMoneyProFile() {
        File[] files = FileUtil.ls(path);
        Optional<File> op = Arrays.stream(files)
                .filter(file -> FileUtil.mainName(file).contains("Money Pro") && FileUtil.extName(file).equals("csv"))
                .findFirst();
        File file = op.orElse(null);
        Assert.notNull(file, "MoneyPro csv file not found");
        return file;
    }

    @Override
    public void exec(List<String> args) {
        String type = "wiz";
        if (CollectionUtil.isNotEmpty(args)) {
            type = args.get(0);
        }

        if ("wiz".equals(type)) {
            pro2Wiz();
        } else if ("pro".equals(type)) {
            pro2Wiz();
        } else {
            log.info("MoneyRunner 转换类型错误:{}", type);
        }
    }

    /**
     * moneyPro 交易记录导入 moneyWiz
     */
    @Test
    public void pro2Wiz() {
        File file = getMoneyProFile();
        List<MoneyWiz> list = parseMoneyPro(file);
        writer(list, "MoneyWiz.csv");
        log.info("导入 MoneyWiz CSV 总记录数:{}", list.size());
    }

    /**
     * 分账户导入
     * moneyPro 交易记录导入 moneyWiz
     */
    @Test
    public void pro2WizForEach() {
        File file = getMoneyProFile();
        List<MoneyWiz> list = parseMoneyPro(file);
        Map<String, List<MoneyWiz>> map = new LinkedHashMap<>();

        for (MoneyWiz moneyWizBean : list) {
            String account = moneyWizBean.getAccount();
            if (!map.containsKey(account)) {
                map.put(account, new ArrayList<>());
            }
            List<MoneyWiz> moneyWizBeans = map.get(account);
            moneyWizBeans.add(moneyWizBean);
        }

        for (Map.Entry<String, List<MoneyWiz>> entry : map.entrySet()) {
            String fileName = "MoneyWiz-" + entry.getKey() + ".csv";
            writer(entry.getValue(), fileName);
        }

        log.info("moneywiz.csv 总记录数:{}", list.size());
        Assert.isTrue(list.size() == totalSize);
    }

    @Test
    public void wiz2Pro() {
        // File file = getMoneyProFile();
        // List<MoneyWizBean> list = parseMoneyPro(file);
        // writer(list, "MoneyWiz.csv");
        // log.info("moneywiz.csv 总记录数:{}", list.size());
    }


    private List<MoneyWiz> parseMoneyPro(File file) {
        List<MoneyPro> list = read(file, MoneyPro.class);
        List<MoneyWiz> moneyWizBeanList = new ArrayList<>(list.size());

        log.debug(JSONUtil.toJsonPrettyStr(list));

        for (MoneyPro copy : list) {
            if (StrUtil.contains(copy.getAccount(), CatalogEnum.FUND.getAccountName())) {
                copy.setTradeType(TradeTypeEnum.INCOME.getType());
                copy.setCatalog(CatalogEnum.FUND.getWizName());
            }

            if (TradeTypeEnum.notContains(copy.getTradeType())) {
                continue;
            }

            // if (copy.isConsume()) {
            //     continue;
            // }

            moneyWizBeanList.add(MoneyWiz.build(copy));
        }

        return moneyWizBeanList;
    }

    private <T extends CsvModel> List<T> read(File file, Class<T> clazz) {
        Assert.notNull(file);

        CsvReadConfig config = CsvReadConfig.defaultConfig();
        config.setContainsHeader(true);
        CsvReader reader = CsvUtil.getReader(config);
        CsvData data = reader.read(file);
        List<String> header = data.getHeader();
        totalSize = data.getRows().size();

        List<T> result = new ArrayList<>();
        log.info("{} CSV 总记录数:{}", clazz.getSimpleName(), data.getRows().size());

        for (CsvRow csvRow : data.getRows()) {
            T bean = ReflectUtil.newInstance(clazz);
            result.add(bean);

            for (int i = 0; i < csvRow.getRawList().size(); i++) {
                // Alias 注解的名字
                String fieldName = header.get(i);
                String value = csvRow.getRawList().get(i);

                if (StrUtil.isBlank(fieldName)) {
                    continue;
                }

                // 使用 Alias 注解的名字 代替 fieldName
                Field field = ReflectUtil.getField(clazz, fieldName);

                if (field == null) {
                    log.error(" CsvRow -> {}出错, 字段[{}]不存在", clazz.getSimpleName(), fieldName);
                    continue;
                }

                ReflectUtil.setFieldValue(bean, field, value == null ? "" : value.trim());
            }
        }
        return result;
    }


    // /**
    //  * 支付宝账单转MoneyPro
    //  */
    // @Test
    // public void toMoneyPro() {
    //     File file = getMoneyProFile();
    //     List<MoneyWizBean> list = parse(file);
    //     writer(list, "MoneyWiz.csv");
    //     log.info("moneywiz.csv 总记录数:{}", list.size());
    //     Assert.isTrue(list.size() == totalSize);
    // }


    private <T extends CsvModel> void writer(List<T> list, String fleName) {
        File newFile = FileUtil.touch(path, fleName);
        CsvWriter writer = CsvUtil.getWriter(newFile, StandardCharsets.UTF_8);
        List<String> data = list.stream().map(t -> t.getCsvData()).collect(Collectors.toList());
        writer.write(list.get(0).getHeader());
        writer.write(data);
        writer.close();
    }
}
