package com.leetcode.page01;

import java.util.Arrays;

/**
 * @author: Jfeng
 * @date: 2018/4/21
 */
public class T04 {
    /**
     * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2 。
     *
     * 请找出这两个有序数组的中位数。要求算法的时间复杂度为 O(log (m+n)) 。
     *
     * 示例 1:
     *
     * nums1 = [1, 3]
     * nums2 = [2]
     * <p>
     * 中位数是 2.0
     *
     * 示例 2:
     *
     * nums1 = [1, 2]
     * nums2 = [3, 4]
     *
     * 中位数是 (2 + 3)/2 = 2.5
     */

    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 2};
        int[] nums2 = new int[]{3, 4};

        T04 t04 = new T04();
        double arrays = t04.findMedianSortedArrays(nums1, nums2);
        System.out.println("arrays = " + arrays);
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] total = new int[nums1.length + nums2.length];
        System.arraycopy(nums1, 0, total, 0, nums1.length);
        System.arraycopy(nums2, 0, total, nums1.length, nums2.length);
        System.out.println(Arrays.toString(total));
        Arrays.sort(total);

        if (total.length % 2 == 0) {
            int sum = total[total.length / 2] + total[total.length / 2 - 1];
            return sum / 2.0;
        }
        return total[total.length / 2];
    }
}
