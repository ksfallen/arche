package com.yhml.core.util;

import java.util.*;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

import static com.yhml.core.util.ValidateUtil.underline;

@Slf4j
public class StringUtil extends StringUtils {

    public static final String DEFAULT_JOIN_SEPARATOR = ",";

    public static String toString(Object obj, String nullStr) {
        return obj == null ? nullStr : obj.toString();
    }

    /**
     * 将驼峰风格替换为下划线风格
     */
    public static String camelToUnderline(String str) {
        final int size;
        final char[] chars;
        final StringBuilder sb = new StringBuilder((size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
        char c;
        for (int i = 0; i < size; i++) {
            c = chars[i];
            if (isUppercaseAlpha(c)) {
                sb.append('_').append(toLowerAscii(c));
            } else {
                sb.append(toUpperAscii(c));
            }
        }
        return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
    }

    /**
     * 将下划线风格替换为驼峰风格
     */
    public static String underlineToCamel(String str) {
        Matcher matcher = underline.matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }
        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }
        return builder.toString();
    }

    public static boolean isUppercaseAlpha(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    public static boolean isLowercaseAlpha(char c) {
        return (c >= 'a') && (c <= 'z');
    }

    public static char toUpperAscii(char c) {
        if (isLowercaseAlpha(c)) {
            c -= (char) 0x20;
        }
        return c;
    }

    public static char toLowerAscii(char c) {
        if (isUppercaseAlpha(c)) {
            c += (char) 0x20;
        }
        return c;
    }

    /**
     * BaseUsers to baseUsers
     */
    public static String toLowerCaseFirst(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(0, toLowerAscii(sb.charAt(0)));
        return sb.toString();
    }

    /**
     * baseUsers to BaseUsers
     */
    public static String toUpperCaseFirst(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(0, toUpperAscii(sb.charAt(0)));
        return sb.toString();
    }

    /**
     * 对字符按照指定的字符字节数进行分割
     *
     * 例子: 按5字节分割 abcweqweqw1231243245 -> [abcwe, qweqw, 12312, 43245]
     *
     * @param str   需要进行分割的字符串
     * @param bytes 多少字符个数进行分割
     * @return 分割后的数组
     */
    public static String[] splitString(String str, int bytes) {
        int loopCount = (str.length() % bytes == 0) ? (str.length() / bytes) : (str.length() / bytes + 1);
        String result[] = new String[loopCount];

        for (int i = 1; i <= loopCount; i++) {
            if (i == loopCount) {
                result[i - 1] = str.substring((i - 1) * bytes, str.length());
            } else {
                result[i - 1] = str.substring((i - 1) * bytes, (i * bytes));
            }

            log.debug(String.format("%s:%s", str, result[i - 1]));
        }

        return result;
    }

    public static boolean hasText(String str) {
        return isNotEmpty(str);
    }

    public static boolean hasLength(String str) {
        return isNotEmpty(str);
    }

    /**
     * 统计线程堆栈
     */
    public String getStackTrace() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] stackTraceElements = entry.getValue();

            if (thread.equals(Thread.currentThread())) {
                continue;
            }

            sb.append("\n 线程:").append(thread.getName());

            for (StackTraceElement element : stackTraceElements) {
                sb.append("\t ").append(element);
            }
        }

        return sb.toString();
    }

    public static List<String> toList(String str) {
        return toList(str, DEFAULT_JOIN_SEPARATOR);
    }

    public static List<String> toList(String str, String separator) {
        List<String> list = new ArrayList<>();
        String[] strArr = str.split(separator);
        Collections.addAll(list, strArr);
        return list;
    }

    public static String join(Iterable<? extends CharSequence> elements) {
        return join(DEFAULT_JOIN_SEPARATOR, elements);
    }

    public static String join(String separator, Iterable<? extends CharSequence> elements) {
        if (elements == null) {
            return "";
        }

        StringJoiner joiner = new StringJoiner(separator);
        elements.forEach(c -> {
            if (isNotBlank(c)) {
                joiner.add(c);
            }
        });

        return joiner.toString();
    }

}
