package com.yhml.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author: Jfeng
 * @date: 2018/4/25
 */
public class ValidateUtil {


    // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
    // 字符串在编译时会被转码一次,所以是 "\\b"
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
    private static String PHONE_REGEX = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry" + "|s" +
            "(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" + "|laystation portable)|nokia|fennec|htc[-_]" + "|mobile|up" +
            ".browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    private static String TABLE_REGEX = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    //移动设备正则匹配：手机端、平板
    private static Pattern phone = Pattern.compile(PHONE_REGEX, Pattern.CASE_INSENSITIVE);
    private static Pattern table = Pattern.compile(TABLE_REGEX, Pattern.CASE_INSENSITIVE);


    public static Pattern underline = Pattern.compile("_[a-zA-Z]");
    public static Pattern words = Pattern.compile("\\w+");
    public static Pattern chinese = Pattern.compile("[\u4e00-\u9fa5]+");
    public static Pattern number = Pattern.compile("[0-9]*");
    public static Pattern href = Pattern.compile("<a[\\s]+href[\\s]*=[\\s]*\"([^<\"]+)\"", Pattern.CASE_INSENSITIVE);

    //请求url匹配主机域名
    public final static String URL_REGEX = "(?i)(http[s]://)([^/]+)";

    //不用拦截的资源路径
    public final static String NOT_INTERCEPT_URI_PATTERN = "^.*\\.(js|css|png|gif|jpg|bmp|jpeg|swf|nois|html)$";

    //允许的图片格式
    public final static String IMAGE_PATTERN = "^.*\\.(png|gif|jpg|bmp|jpeg)$";

    public static boolean isWords(String value) {
        return words.matcher(value).matches();
    }

    public static boolean isProeritestKey(String value) {
        return value.matches("\\$\\{(\\w+\\.)+\\w+}");
    }

    public static boolean isChinese(CharSequence input) {
        return chinese.matcher(input).matches();
    }

    public static String getHref(String html) {
        Matcher m = href.matcher(html);

        StringBuilder ret = new StringBuilder();
        while (m.find()) {
            ret.append(m.group(1)).append("\n");
        }

        return ret.toString();
    }

    public boolean isNumeric(String str) {
        return number.matcher(str).matches();
    }

    /**
     *  获取主域名
     */
    public static String getRequestUrlDomain(String requestUrl){
        Matcher matcher = Pattern.compile(URL_REGEX).matcher(requestUrl);
        return matcher.find() ?  matcher.group() : null;
    }

    /**
     * 验证uri是否被允许的
     * @param uri
     */
    public static boolean isUriAllowable(String uri){
        return Pattern.compile(NOT_INTERCEPT_URI_PATTERN).matcher(uri).matches();
    }

    /**
     *是否允许的图片格式
     */
    public static boolean isImage(String name){
        return Pattern.compile(IMAGE_PATTERN).matcher(name).matches();
    }

    /**
     * 检测是否是移动设备访问
     *
     * @param userAgent 浏览器标识
     * @return true:移动设备接入，false:pc端接入
     */
    public static boolean is(String userAgent) {
        if (StringUtil.isBlank(userAgent)) {
            return false;
        }
        // 匹配
        Matcher matcherPhone = phone.matcher(userAgent);
        Matcher matcherTable = table.matcher(userAgent);
        return matcherPhone.find() || matcherTable.find();
    }

    /**
     * 获取手机型号
     */
    public static String getCellPoneModel(String userAgent) {
        Matcher matcher = phone.matcher(userAgent);
        return matcher.find() ? matcher.group(1).trim() : null;
    }


    /**
     * 校验手机号
     */
    public static boolean isMobile(String value) {
        String regex = "^1[3578]\\d{9}$";
        return StringUtil.isNoneBlank(value) && Pattern.matches(regex, value);
    }

    /**
     * 校验固定电话
     */
    public static boolean isTelephoneNumber(String value) {
        String regex = "^((0\\\\d{2,3})-)(\\\\d{7,8})(-(\\\\d{3,}))?$";
        return StringUtil.isNoneBlank(value) && Pattern.matches(regex, value);
    }

    /**
     * 校验电子邮箱
     */
    public static boolean isEmail(String value) {
        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return StringUtil.isNoneBlank(value) && Pattern.matches(regex, value);
    }

    /**
     * 校验出生日期
     * yyyyMMdd
     */
    public static boolean isBirthday(String value) {
        String regex = "\\d{4}[0-1]\\d[0-3]\\d{2}";
        return StringUtil.isNoneBlank(value) && Pattern.matches(regex, value);
    }

    /**
     * 校验邮政编码
     */
    public static boolean isZipcode(String value) {
        String regex = "[0-9]\\d{5}(?!\\d)";
        return StringUtil.isNoneBlank(value) && Pattern.matches(regex, value);
    }

    /**
     * 校验密码复杂度
     * @param password--8~20位的必须包含数字或字母或特殊符号中的两种
     * @return
     */
    public static boolean verifyPassword(String password) {
        // 判断密码是否包含数字：包含返回1，不包含返回0
        int i = password.matches(".*\\d+.*") ? 1 : 0;

        // 判断密码是否包含字母：包含返回1，不包含返回0
        int j = password.matches(".*[a-zA-Z]+.*") ? 1 : 0;

        // 判断密码是否包含特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)：包含返回1，不包含返回0
        int k = password.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;

        // 判断密码长度
        int l = password.length();

        return i + j + k >= 2 && l >= 8 && l <= 20;
    }

    public static boolean isDigits(String str) {
        return NumberUtils.isDigits(str);
    }
}
