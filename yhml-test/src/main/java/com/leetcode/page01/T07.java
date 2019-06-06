package com.leetcode.page01;

/**
 * @author: Jfeng
 * @date: 2018/7/19
 */
public class T07 {
    /**
     * 给定一个 32 位有符号整数，将整数中的数字进行反转。
     *
     * 示例 1:
     *
     * 输入: 123
     * 输出: 321
     * 示例 2:
     *
     * 输入: -123
     * 输出: -321
     * 示例 3:
     *
     * 输入: 120
     * 输出: 21
     * 注意:
     *
     * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231,  231 − 1]。根据这个假设，如果反转后的整数溢出，则返回 0。
     */


    public static void main(String[] args) {
        reverse(123);
    }

    public static int reverse(int x) {
        // System.out.println(Integer.MAX_VALUE); //  2147483647
        // System.out.println(Integer.MIN_VALUE); // -2147483648

        int ret = 0;

        while (x != 0) {
            int pop = x % 10;
            x = x / 10;

            if (ret > Integer.MAX_VALUE / 10 || (ret == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }

            if (ret < Integer.MIN_VALUE / 10 || (ret == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }

            ret = ret * 10 + pop;
        }
        System.out.println(ret);
        return ret;
    }


}
