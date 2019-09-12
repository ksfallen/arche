package com.yhml.test.leetcode.page01;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jfeng
 * @date: 2018/4/20
 */
public class T03 {

    /**
     * 给定一个字符串，找出不含有重复字符的最长子串的长度。
     * <p>
     * 示例：
     * <p>
     * 给定 "abcabcbb" ，没有重复字符的最长子串是 "abc" ，那么长度就是3。
     * <p>
     * 给定 "bbbbb" ，最长的子串就是 "b" ，长度是1。
     * <p>
     * 给定 "pwwkew" ，最长子串是 "wke" ，长度是3。请注意答案必须是一个子串，"pwke" 是 子序列  而不是子串。
     */

    public static void main(String[] args) {
        int length = lengthOfLongestSubstring("pwwkew");
        System.out.println(length);
    }

    public static int lengthOfLongestSubstring(String s) {
        List<Character> list = new ArrayList<>();
        int n = s.length();
        int max = 0;

        for (int i = 0; i < n; i++) {
            char charAt = s.charAt(i);

            if (list.contains(charAt)) {
                int index = list.indexOf(charAt);
                boolean isremove = false;
                for (int j = 0; j <= index; j++) {
                    list.remove(0);
                    isremove = true;
                }

                if (isremove) {
                    list.add(charAt);
                }
            } else {
                list.add(charAt);
                max = Math.max(max, list.size());
            }
        }

        return max;
    }
}
