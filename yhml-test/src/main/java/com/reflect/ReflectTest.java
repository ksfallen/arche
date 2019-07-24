package com.test.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * @author: Jfeng
 * @date: 2019-05-30
 */
public class ReflectTest {
    public void method1(Integer a) {
        System.out.println(a);
    }

    public static void main(String[] args) {
        Method[] methods = ReflectTest.class.getMethods();

        Parameter[] parameters = methods[0].getParameters();

        Arrays.stream(parameters).forEach(parameter -> System.out.println(parameter.getName()));
    }
}
