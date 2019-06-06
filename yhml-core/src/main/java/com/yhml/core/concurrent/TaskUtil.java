package com.yhml.core.concurrent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/11/29
 */
public class TaskUtil {

    public static void main(String[] args) {
        timeTask(100, 100, () -> UUID.randomUUID().toString());
    }

    /**
     * 测试 并发线程
     *
     * @param nThreads  线程数
     * @param singleNum 单个线程执行个数
     * @param task      任务
     * @return
     */
    public static long timeTask(int nThreads, int singleNum, final Runnable task) {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        ThreadFactory tf = Executors.defaultThreadFactory();

        final AtomicLong sum = new AtomicLong();
        final AtomicLong min = new AtomicLong(10000);
        final AtomicLong max = new AtomicLong(0);


        for (int i = 0; i < nThreads; i++) {
            Thread thread = tf.newThread(() -> {
                try {
                    startGate.await();
                    for (int j = 0; j < singleNum; j++) {
                        long start = System.nanoTime();
                        try {
                            task.run();
                        } finally {
                            long end = System.nanoTime();
                            long at = (end - start) / 1000 / 1000;
                            sum.addAndGet(at);
                            if (min.get() > at) {
                                min.getAndSet(at);
                            }
                            if (max.get() < at) {
                                max.getAndSet(at);
                            }
                        }
                    }
                } catch (InterruptedException ignored) {
                } finally {
                    endGate.countDown();
                }
            });

            thread.start();
        }

        startGate.countDown();
        long st = System.currentTimeMillis();

        try {
            endGate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long ed = System.currentTimeMillis() - st;

        int taskNum = nThreads * singleNum;

        System.out.println("--------------------");
        System.out.println("执行任务数: " + taskNum);
        System.out.println("--------------------");
        System.out.println("所有线程共耗时:" + div(sum.get(), 1000) + " s");
        System.out.println("并发执行完成耗时:" + div(ed, 1000) + " s");
        System.out.println("单任务平均耗时:" + div(sum.get(), taskNum) + " ms");
        System.out.println("单线程最小耗时:" + div(min.get(), 1) + " ms");
        System.out.println("单线程最大耗时:" + div(max.get(), 1) + " ms");

        return 0;
    }

    private static double div(long n1, long n2) {
        BigDecimal decimal = NumberUtils.createBigDecimal(n1 + "");
        BigDecimal decima2 = NumberUtils.createBigDecimal(n2 + "");
        return decimal.divide(decima2, 4, RoundingMode.FLOOR).doubleValue();
    }
}
