package com.yhml.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 精度运算
 */
public class MathUtil extends NumberUtils {

    public static final String DECIMAL_FORMAT_2 = "2";
    public static final String DECIMAL_FORMAT_3 = "3";
    private static final int DEFAULT_DIV_SCALE = 5;
    private static final int DEFAULT_ROUNDINGMODE = BigDecimal.ROUND_HALF_UP;
    private static final DecimalFormat decimalFormat1 = new DecimalFormat("0");
    private static final DecimalFormat decimalFormat2 = new DecimalFormat("0.00");
    private static final DecimalFormat decimalFormat3 = new DecimalFormat("0.000");
    private static final BigDecimal one = new BigDecimal("1");
    /** 百分号 */
    public static DecimalFormat num = new DecimalFormat();

    static {
        num.applyPattern("0.00%");
    }


    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        return round(v1.add(v2));
    }

    public static double add(double v1, double v2) {
        return new BigDecimal(v1).add(new BigDecimal(v2)).doubleValue();
    }

    public static double add(double... value) {
        BigDecimal ret = new BigDecimal(0);

        for (double v : value) {
            BigDecimal temp = new BigDecimal(Double.toString(v));
            ret = ret.add(temp);
        }

        return ret.doubleValue();
    }


    public static BigDecimal sub(BigDecimal minuend, BigDecimal subtrahend) {
        return round(minuend.subtract(subtrahend));
    }

    /**
     * 减法
     *
     * @return
     */
    public static double sub(double v1, double v2) {
        return new BigDecimal(v1).subtract(new BigDecimal(v2)).doubleValue();
    }


    public static BigDecimal mul(BigDecimal multiplier, BigDecimal multiplicand) {
        return round(multiplier.multiply(multiplicand));
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        return new BigDecimal(v1).multiply(new BigDecimal(v2)).doubleValue();
    }

    public static double mulAndRound(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return round(b1.multiply(b2).doubleValue(), 2);
    }


    public static BigDecimal div(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, DEFAULT_DIV_SCALE, DEFAULT_ROUNDINGMODE);
    }


    public static double div(double v1, double v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指   定精度，以后的数字四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return
     */

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.divide(b2, scale, DEFAULT_ROUNDINGMODE).doubleValue();
    }

    public static double round(double v) {
        return round(v, DEFAULT_DIV_SCALE);
    }

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String format(Double v, String type) {
        String str = "";
        double d = getDouble(v);

        switch (type) {
            case DECIMAL_FORMAT_2:
                str = format2(d);
                break;
            case DECIMAL_FORMAT_3:
                str = format3(d);
                break;
            default:
                str = format(d);
                break;
        }

        return str;
    }

    public static String format(Double v) {
        return format(getDouble(v));
    }

    public static String format(double v) {
        return decimalFormat1.format(v);
    }

    public static String format2(double v) {
        return decimalFormat2.format(v);
    }

    public static String format3(double v) {
        return decimalFormat3.format(v);
    }

    public static BigDecimal round(BigDecimal bigDecimal) {
        return bigDecimal.setScale(DEFAULT_DIV_SCALE, DEFAULT_ROUNDINGMODE);
    }


    public static String formatDouble(double d) {
        String res = num.format(d);
        return res.equals("-0.00%") ? "0.00%" : res;
    }

    public static int getInt(Integer v) {
        return v == null ? 0 : v.intValue();
    }

    public static double getDouble(Double v) {
        return v == null ? 0D : v.doubleValue();
    }

    public static long getLong(Long v) {
        return v == null ? 0L : v.longValue();
    }

}
