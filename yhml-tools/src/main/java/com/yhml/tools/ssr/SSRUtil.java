package com.yhml.tools.ssr;

import com.yhml.tools.ssr.baidu.BaiduOcrResult;
import com.yhml.tools.ssr.baidu.WordsResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jfeng
 * @date 2020/3/12
 */
public class SSRUtil {
    public static List<SSRBean> parse(BaiduOcrResult words) {
        List<SSRBean> list = new ArrayList<>();
        int i = 0;
        SSRBean bean = new SSRBean();
        for (WordsResult word : words.getWordsResult()) {
            String value = word.getWords().trim().replaceAll("[()\\s协议混淆]", "");
            if (i == 0) {
                bean = new SSRBean();
                list.add(bean);
            }

            // @formatter:off
            switch (i) {
                case 0: break;
                case 1: bean.setServer(cleanValue(value)); break;
                case 2: bean.setPort(cleanValue(value)); break;
                case 3: bean.setPassword(cleanValue(value)); break;
                case 4: bean.setMethod(cleanValue(value)); break;
                case 5: bean.setProtocol(cleanValue(value)); break;
                case 6:
                    bean.setObfs(cleanValue(value));
                    i = 0;
                    break;
                default:
            }
            // @formatter:on
        }

        return list;
    }

    /**
     * 2020年3月版本
     */
    public static List<SSRBean> parse2(BaiduOcrResult words) {
        List<SSRBean> list = new ArrayList<>();
        List<String> result = words.getWordsResult().stream().map(w -> getValue(w)).collect(Collectors.toList());
        int count = 0;
        int index = 0;
        SSRBean bean = null;
        for (String value : result) {
            if (count++ == 0) {
                bean = new SSRBean();
                list.add(bean);
            }
            if (value.contains("端口")) {
                bean.setServer(cleanValue(result.get(index - 1)));
                bean.setPort(cleanValue(result.get(index + 1)));
            }
            if (value.contains("密码")) {
                bean.setPassword(cleanValue(result.get(index + 1)));
            }
            if (value.contains("加密")) {
                bean.setMethod(cleanValue(result.get(index + 1)));
            }
            if (value.contains("协议")) {
                bean.setProtocol(cleanValue(result.get(index + 1)));
            }
            if (value.contains("混淆")) {
                count = 0;
                bean.setObfs(cleanValue(result.get(index + 1)));
            }
            index++;
        }
        return list;
    }

    private static String cleanValue(String value) {
        value = value.replaceAll("\\s", "");
        String[] split = value.split(":");
        return split.length == 1 ? split[0].toLowerCase().trim() : split[1].toLowerCase().trim();
    }

    private static String getValue(WordsResult wordsResult) {
        return wordsResult.getWords().replaceAll("\\s", "");
    }


}
