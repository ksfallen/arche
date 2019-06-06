package com.yhml.core.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Jfeng
 * @date: 2018/8/23
 */
public class ExecutorServiceUtil {

    private static final ThreadFactory THREAD_FACTORY = create("default");

    public static ThreadFactory create(String threadName, boolean isDaemon) {
        return new ThreadFactory() {

            private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                Thread thread = defaultFactory.newThread(r);
                thread.setDaemon(true);
                thread.setName(threadName + "-" + threadNumber.getAndIncrement());
                return thread;
            }
        };
    }

    public static ThreadFactory create(String threadName) {
        return create(threadName, false);
    }

    static public ScheduledExecutorService newScheduledExecutorService(int poolSize, ThreadFactory factory) {
        return new ScheduledThreadPoolExecutor(poolSize, factory);
    }


    static public ExecutorService newExecutorService(int poolSize, ThreadFactory factory) {
        if (factory == null) {
            factory = THREAD_FACTORY;
        }
        return new ThreadPoolExecutor(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), factory);
    }

    static public void shutdown(ExecutorService executorService) {
        executorService.shutdownNow();
    }
}
