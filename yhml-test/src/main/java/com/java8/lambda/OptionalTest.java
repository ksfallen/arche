package com.java8.lambda;

import java.util.Optional;

/**
 * @author: Jfeng
 * @date: 2019-06-10
 */
public class OptionalTest {
    public static void main(String[] args) {
        Object o = Optional.empty().orElse(null);

        System.out.println(Double.NaN);
    }
}
