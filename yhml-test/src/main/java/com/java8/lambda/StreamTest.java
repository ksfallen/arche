package com.java8.lambda;

import java.util.*;
import java.util.stream.IntStream;

import org.junit.Test;

import static java.util.stream.Collectors.toList;

/**
 * @author: Jfeng
 * @date: 2018/6/29
 */
public class StreamTest {
    public static void main(String[] args) {


    }

    public static void test() {
        List<String> list = new ArrayList<>();
        list.stream().filter(s -> s.equals("a")).forEach(s -> s.toLowerCase());
    }

    public static void test2() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        IntStream stream = list.stream().mapToInt(Integer::intValue);
        System.out.println(stream.sum());
    }

    @Test
    public void sort() {
        List<Integer> list = Arrays.asList(6, 7, 5, 8, 4, 3, 9, 2, 0, 1, 12, 34, 56, 78, 94);

        // 升序排序 o1小于o2，返回-1（负数），相等返回0，01大于02返回1（正数）
        // 降序排序 o1小于o2，返回1（正数）， 相等返回0，01大于02返回-1（负数）

        // 升序
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        System.out.println("升序:" + list);

        // 倒序
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        System.out.println("倒序:" + list);

        // 升序
        list.sort(Comparator.comparing(Integer::intValue));
        System.out.println("升序:" + list);

        // 倒序
        list.sort(Comparator.comparing(Integer::intValue).reversed());
        System.out.println("倒序:" + list);

        // list没变化, 返回值升序
        List<Integer> sorted = list.stream().sorted(Comparator.comparing(Integer::intValue)).collect(toList());
        System.out.println("升序:" + list);
        System.out.println("返回值升序:" + sorted);

        // 倒序
        sorted.sort(Comparator.reverseOrder());
        System.out.println("倒序:" + sorted);

    }
}
