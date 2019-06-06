package com.yhml.core.util;

import java.util.StringJoiner;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/10/23
 */
@Slf4j
public class PinyinUtil {

    private static HanyuPinyinOutputFormat lower_format = new HanyuPinyinOutputFormat();
    // private static HanyuPinyinOutputFormat upper_format = new HanyuPinyinOutputFormat();

    static {
        lower_format.setCaseType(HanyuPinyinCaseType.LOWERCASE);       // 输出拼音全部小写
        lower_format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);    // 不带声调

        // upper_format.setCaseType(HanyuPinyinCaseType.UPPERCASE);    // 输出拼音全部大写
        // upper_format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 不带声调
    }


    public static void main(String[] args) {
        System.out.println(getPinyin("开启"));
        System.out.println(getPinyinWithUnderline("开启"));
        System.out.println(getFirstChar("开启"));
        System.out.println(getFirstLetters("开启"));
        System.out.println(getFirstLettersUp("开启"));
    }


    /**
     * 将文字转为汉语拼音
     *
     * @param chineselanguage 要转成拼音的中文
     */
    public static String getPinyin(String input) {
        char[] chars = input.trim().toCharArray();
        StringBuilder sb = new StringBuilder();

        // lower_format.setVCharType(HanyuPinyinVCharType.WITH_V);

        for (char aChar : chars) {
            sb.append(toConvert(lower_format, aChar));
        }

        return sb.toString();
    }

    public static String getPinyinWithUnderline(String input) {
        char[] chars = input.trim().toCharArray();
        // lower_format.setVCharType(HanyuPinyinVCharType.WITH_V);

        StringJoiner joiner = new StringJoiner("_");
        for (char aChar : chars) {
            joiner.add(toConvert(lower_format, aChar));
        }

        return joiner.toString();
    }

    /**
     * 取第一个汉字的第一个字符, 大写
     */
    public static String getFirstChar(String input) {
        char[] chars = input.trim().toCharArray();
        return toConvertFirst(lower_format, chars[0]).toUpperCase();
    }

    /**
     * 取拼音首字母
     * 自己定义 format
     */
    public static String getFirstLetters(String input, HanyuPinyinOutputFormat format) {
        char[] chars = input.trim().toCharArray();
        StringBuilder sb = new StringBuilder();

        for (char aChar : chars) {
            sb.append(toConvertFirst(format, aChar));
        }

        return sb.toString();
    }

    /**
     * 取拼音首字母 大写
     */
    public static String getFirstLetters(String input) {
        return getFirstLetters(input, lower_format);
    }

    /**
     * 取拼音首字母 小写
     */
    public static String getFirstLettersUp(String input) {
        return getFirstLetters(input).toUpperCase();
    }

    /**
     * 如果字符是中文,则将中文转为汉语拼音
     *
     * @param format
     * @param aChar
     * @return
     */
    private static String toConvert(HanyuPinyinOutputFormat format, char aChar) {
        String input = String.valueOf(aChar);

        String result = "";

        try {
            // 如果是中文,则将中文转为汉语拼音
            if (ValidateUtil.isChinese(input)) {
                result = PinyinHelper.toHanyuPinyinStringArray(aChar, format)[0];
            } else {
                result = String.valueOf(aChar);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            // log.error("字符转拼音失败:{}", input, e);
            throw new IllegalArgumentException("input:" + input);
        }

        return result;
    }

    /**
     * 取首字母
     */
    private static String toConvertFirst(HanyuPinyinOutputFormat format, char aChar) {
        char c = toConvert(format, aChar).charAt(0);
        return String.valueOf(c);
    }


}
