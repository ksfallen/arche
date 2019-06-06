package com.leetcode.page01;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jfeng
 * @date: 2018/4/19
 */
public class T01 {

    /**
     * 给定 nums = [2, 7, 11, 15], target = 9
     *
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     */

    public static void main(String[] args) {

        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        int[] ret = twoSum(nums, target);
        System.out.println(Arrays.toString(ret));
    }

    public static int[] twoSum(int[] nums, int target) {
        // key=值, index=下标
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++){
            int key = target - nums[i];
            if (map.containsKey(key)) {
                return new int[]{map.get(key), i};
            }

            // map 中不存在 key ,就保存
            map.put(nums[i] , i);
        }
        return null;

    }
}
