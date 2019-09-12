package com.yhml.test.java;

import com.yhml.core.base.bean.Result;
import org.junit.Test;

import cn.hutool.core.bean.BeanPath;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * @author: Jfeng
 * @date: 2019-07-16
 */
public class BeanTest {

    public void test1() {
        int a = 1 + 2;
        int b = 1 + 2;

    }


    @Test
    public void test() {
        Result result = Result.ok();
        BeanPath path = BeanPath.create("result.msg");
        System.out.println(path.get(result));
    }


    @Test
    public void reflectTest() {
        Method[] methods = BeanTest.class.getMethods();
        Parameter[] parameters = methods[0].getParameters();
        Arrays.stream(parameters).forEach(parameter -> System.out.println(parameter.getName()));
    }

    @Test
    public void async() throws ExecutionException, InterruptedException {
        CompletableFuture cf = CompletableFuture.completedFuture("message");
        assertTrue(cf.isDone());
        assertEquals("message", cf.getNow(null));
        System.out.println(cf.get());
    }

    @Test
    public void optional() {
        Object o = Optional.empty().orElse(null);
        System.out.println("o = " + o);
    }

}
