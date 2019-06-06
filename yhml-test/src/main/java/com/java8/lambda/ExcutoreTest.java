package com.java8.lambda;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * @author: Jfeng
 * @date: 2018/12/7
 */
public class ExcutoreTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // ExecutorService service = Executors.newScheduledThreadPool(2);
        // ExecutorService service2 = Executors.newFixedThreadPool(2);

        completedFutureExample();

    }

    static void completedFutureExample() throws ExecutionException, InterruptedException {
        CompletableFuture cf = CompletableFuture.completedFuture("message");
        assertTrue(cf.isDone());
        assertEquals("message", cf.getNow(null));

        System.out.println(cf.get());
    }
}
