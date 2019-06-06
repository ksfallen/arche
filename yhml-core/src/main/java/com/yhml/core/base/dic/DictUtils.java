package com.yhml.core.base.dic;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;


/**
 * 检查字典字段取值是否在有效范围内
 *
 * @author admin
 */
public class DictUtils {

    public static void main(String[] args) {
        checkValid("productStatus", "ENABLE");
        getDictKeys("productStatus");
    }

    /**
     * @param dictName 字段名,驼峰式
     * @param value    字典值
     * @return
     */
    public static boolean checkValid(String dictName, String value) {
        if (DictCons.dicts.get(dictName) == null) {
            throw new UnsupportedOperationException("未定义的数据字典:" + dictName);
        }
        if (DictCons.dicts.get(dictName).containsKey(value)) {
            return true;
        }
        String subdicts = "";
        for (DictEntry subdict : DictCons.dicts.get(dictName).values()) {
            subdicts = subdicts + subdict.getKey() + ",";
        }
        throw new UnsupportedOperationException("数据字典" + dictName + "不包含取值" + value + ",有效取值为:" + subdicts.substring(0, subdicts.length() - 1));
    }

    public static boolean isValid(String dictName, String value) {
        try {
            return checkValid(dictName, value);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }

    public static String getDictEntryName(String dictName, String key) {
        if (checkValid(dictName, key)) {
            HashMap<String, DictEntry> dict = DictCons.dicts.get(dictName);
            return dict.get(key).getName();
        }
        return null;
    }

    public static DictEntry getDictEntry(String dictName, String key) {
        if (checkValid(dictName, key)) {
            HashMap<String, DictEntry> dict = DictCons.dicts.get(dictName);
            return dict.get(key);
        }
        return null;
    }

    public static String getDictKeys(String dictName) {
        String subdicts = "";
        for (DictEntry subdict : DictCons.dicts.get(dictName).values()) {
            subdicts = subdicts + subdict.getKey() + ",";
        }
        return StringUtils.trimTrailingCharacter(subdicts, ',');
    }
}
