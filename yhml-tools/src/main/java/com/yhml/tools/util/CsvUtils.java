package com.yhml.tools.util;

import com.yhml.tools.constants.ToolConstants;
import com.yhml.tools.model.CsvModel;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.csv.*;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jfeng
 * @date 2020/8/27
 */
@Slf4j
public class CsvUtils extends CsvUtil {

    private static CsvReadConfig config = CsvReadConfig.defaultConfig();

    static {
        config.setContainsHeader(true);
    }

    public static <T> List<T> read(File file, Class<T> clazz) {
        Assert.notNull(file);
        CsvData data = new CsvReader(config).read(file);
        log.info("CSV记录总数: {}", data.getRows().size());
        List<String> header = data.getHeader().stream().map(s -> s.trim()).collect(Collectors.toList());
        return data.getRows().stream().map(row -> toBean(header, row, clazz)).collect(Collectors.toList());
    }

    public static <T> T toBean(List<String> header, CsvRow csvRow, Class<T> clazz) {
        T bean = ReflectUtil.newInstance(clazz);
        for (int i = 0; i < header.size(); i++) {
            // Alias 注解的名字
            String fieldName = header.get(i);
            String value = csvRow.getRawList().get(i).trim();
            if (StrUtil.isNotBlank(fieldName)) {
                // 使用 Alias 注解的名字 代替 fieldName
                Field field = ReflectUtil.getField(clazz, fieldName);
                if (field != null) {
                    ReflectUtil.setFieldValue(bean, field, value == null ? "" : value.trim());
                } else {
                    log.debug("CsvRow -> {}出错, 字段[{}]不存在", clazz.getSimpleName(), fieldName);
                }
            }
        }
        return bean;
    }

    public static void writer(List<? extends CsvModel> list, String file) {
        if (list.isEmpty()) {
            return;
        }
        File newFile = FileUtil.touch(ToolConstants.DOWNLOAD_PATH, file);
        CsvWriter writer = CsvUtil.getWriter(newFile, CharsetUtil.CHARSET_UTF_8);
        List<String> data = list.stream().map(t -> t.toCsvData()).collect(Collectors.toList());
        writer.write(list.get(0).toHeader());
        writer.write(data);
        writer.close();
    }

    public static File getCsvBill(String fileName) {
        File[] files = FileUtil.ls(ToolConstants.DOWNLOAD_PATH);
        Optional<File> op = Arrays.stream(files)
                .sorted(Comparator.comparing(File::lastModified).reversed())
                .filter(file -> FileUtil.mainName(file).contains(fileName) && FileUtil.extName(file).equals("csv"))
                .findFirst();
        return op.orElseThrow(() -> new RuntimeException("未找到账单文件:" + fileName));
    }
}
