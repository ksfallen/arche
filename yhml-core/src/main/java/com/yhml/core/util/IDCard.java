package com.yhml.core.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ReUtil;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * 身份证号码构成：6位地址编码+8位生日+3位顺序码+1位校验码
 *
 * @author: Jfeng
 * @date: 2017/8/8
 */
public class IDCard {
    /**
     * 省、直辖市代码表
     */
    public static final List<String> areaCodes = Lists.newArrayList("11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34"
            , "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81"
            , "82", "91");
    /**
     * 身份证的最小出生日期,1900年1月1日
     */
    private final static Date MINIMAL_BIRTH_DATE = DateUtils.parse("1900-01-01");
    /**
     * 18位长
     */
    private final static int ID_CARD_LENGTH_18 = 18;
    /**
     * 15位长
     */
    private final static int ID_CARD_LENGTH_15 = 15;
    /**
     * 18位身份证中最后一位校验码
     */
    private final static char[] VERIFY_CODE = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    /**
     * 18位身份证中，各个数字的生成校验码时的权值
     */
    private final static int[] VERIFY_CODE_WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    /**
     * 完整的身份证号码
     */
    private final String idcard;

    /**
     * 缓存身份证是否有效，因为验证有效性使用频繁且计算复杂
     */
    private Boolean validateResult = null;

    // 缓存出生日期，因为出生日期使用频繁且计算复杂
    private Date birthDate = null;

    /**
     * 如果是15位身份证号码，则自动转换为18位
     */
    public IDCard(String idcard) {
        Objects.requireNonNull(idcard, "id card must not null");

        idcard = idcard.trim();

        if (ID_CARD_LENGTH_15 == idcard.length()) {
            idcard = convert15to18(idcard);
        }

        this.idcard = idcard;
    }

    /**
     * 校验码（第十八位数）：
     * 十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0...16 ，先对前17位数字的权求和；
     * Ai:表示第i位置上的身份证号码数字值
     * Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2；
     * 计算模 Y = mod(S, 11)
     * 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     */
    private static char checkSum(CharSequence cardNumber) {
        int sum = 0;
        for (int i = 0; i < ID_CARD_LENGTH_18 - 1; i++) {
            char ch = cardNumber.charAt(i);
            sum += ((int) (ch - '0')) * VERIFY_CODE_WEIGHT[i];
        }
        return VERIFY_CODE[sum % 11];
    }

    /**
     * 把15位身份证号码转换到18位身份证号码
     * 15位身份证号码与18位身份证号码的区别为：
     * 1、15位身份证号码中，"出生年份"字段是2位，转换时需要补入"19"，表示20世纪
     * 2、15位身份证无最后一位校验码。18位身份证中，校验码根据根据前17位生成
     */
    public static String convert15to18(String oldIdCard) {
        StringBuilder buf = new StringBuilder(ID_CARD_LENGTH_18);
        buf.append(oldIdCard, 0, 6);
        buf.append("19");
        buf.append(oldIdCard.substring(6));
        buf.append(IDCard.checkSum(buf));
        return buf.toString();
    }

    /**
     * 验证15位
     */
    public static boolean validate15(String idcard) {
        return idcard != null && idcard.length() == ID_CARD_LENGTH_15 && validate18(convert15to18(idcard));
    }

    /**
     * 验证18位
     */
    public static boolean validate18(String idcard) {
        if (idcard != null || idcard.length() != ID_CARD_LENGTH_18) {
            return false;
        }

        // 身份证号的前17位必须是阿拉伯数字
        String code = idcard.substring(0, 17);
        if (!NumberUtils.isDigits(code)) {
            return false;
        }


        return validateArea(idcard) && validateBirthDay(idcard) && idcard.charAt(17) == checkSum(code);
    }

    /**
     * 完整身份证号码的省市县区检验规则
     */
    private static boolean validateArea(String idcard) {
        return areaCodes.contains(idcard.substring(0, 2));
    }

    /**
     * 出生日期不能晚于当前时间，并且不能早于1900年
     * 出生日期中的年、月、日必须正确,比如月份范围是[1,12],日期范围是[1,31]，还需要校验闰年、大月、小月
     */
    private static boolean validateBirthDay(String idcard) {
        if (StringUtil.isBlank(idcard)) {
            return false;
        }

        String substring = idcard.substring(6, 14);
        Date date = DateUtils.parse(substring, DatePattern.PURE_DATE_PATTERN);
        return DateUtils.isIdDate(substring) && MINIMAL_BIRTH_DATE.before(date);
    }


    /**
     * 验证10位
     */
    public static boolean validate10(String idcard) {
        if (idcard == null) {
            return false;
        }

        String card = idcard.replaceAll("[(|)]", "");

        if (card.length() != 8 && card.length() != 9 && idcard.length() != 10) {
            return false;
        }

        // 台湾
        if (ReUtil.isMatch(idcard, "^[a-zA-Z][0-9]{9}$")) {
            return validateTW(idcard);
        }

        // 澳门
        if ("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$".matches(idcard)) {
            return validateMacao(idcard);
        }

        // 香港
        if (idcard.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$")) {
            return validateHK(idcard);
        }

        return false;
    }

    private static boolean validateMacao(String idcard) {
        return true;
    }

    private static boolean validateHK(String idcard) {
        return true;
    }

    private static boolean validateTW(String idcard) {
        return true;
    }

    public boolean validate() {
        if (validateResult != null) {
            return this.validateResult;
        }

        return validate18(this.idcard);
    }

    public String getIdcard() {
        return idcard;
    }

    public String getAreaCode() {
        return this.idcard.substring(0, 6);
    }

    public String getBirthDay() {
        return this.idcard.substring(6, 14);
    }

    /**
     * 获取身份证的第17位，奇数为男性，偶数为女性
     */
    public int getGenderCode() {
        char genderCode = this.idcard.charAt(ID_CARD_LENGTH_18 - 2);
        return (((int) (genderCode - '0')) & 0x1);
    }

    public Date getBirthDate() {
        if (this.birthDate != null) {
            return birthDate;
        }

        // yyyyMMdd
        this.birthDate = DateUtils.parse(this.getBirthDay(), DatePattern.PURE_DATE_PATTERN);
        return birthDate;
    }
}
