package com.atguigu.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
//        <dependency>
//            <groupId>com.google.guava</groupId>
//            <artifactId>guava</artifactId>
//            <version>19.0</version>
//        </dependency>
public class ThreadPoolUtil {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = ThreadPoolUtil.getThreadPoolExecutor(null, null, null);
        pool.execute(() -> {});
        ThreadPoolUtil.awaitTaskCompelete(pool, true);
    }
    public static ThreadPoolExecutor getThreadPoolExecutor(String nameFormat,
                                                           Integer corePoolSize,
                                                           Integer maximumPoolSize

                                                           ) {
        if (corePoolSize == null) {
            corePoolSize = 5;
        }
        if (nameFormat == null) {
            nameFormat = "demo-pool-%d";
        }
        if (maximumPoolSize == null) {
            maximumPoolSize = Integer.MAX_VALUE;
        }
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(nameFormat).build();

        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static void awaitTaskCompelete(ThreadPoolExecutor pool, boolean shutdown) {
        for (; ; ) {
            if (pool.getCompletedTaskCount() == pool.getTaskCount()) {
                break;
            }
        }
        if (shutdown) {
            pool.shutdown();
        }
    }
}
