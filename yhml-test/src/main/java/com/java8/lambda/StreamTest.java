package com.java8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
