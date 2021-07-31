package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池监控demo
 *
 * @author zhangchangyong
 * @date 2021-07-13 18:06
 */
public class TheadPoolMonitor {

    private static ExecutorService threadPoolExecutor = new ThreadPoolExecutor(50,
            100,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1000),
            new MonitorThreadPoolFactory(),
            new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1000; i++) {
            threadPoolExecutor.execute(() -> {
                // System.out.print(1);
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) threadPoolExecutor);
        while (true) {
            System.out.println();
            int queueSize = tpe.getQueue().size();
            System.out.println("当前排队任务数：" + queueSize);
            int activeCount = tpe.getActiveCount();
            System.out.println("当前活动任务数：" + activeCount);
            long completedTaskCount = tpe.getCompletedTaskCount();
            System.out.println("执行完成任务数：" + completedTaskCount);
            long taskCount = tpe.getTaskCount();
            System.out.println("总任务数：" + taskCount);
            System.out.println("corePoolSize：" + tpe.getCorePoolSize());
            System.out.println("largestPoolSize：" + tpe.getLargestPoolSize());
            System.out.println("maximumPoolSize：" + tpe.getMaximumPoolSize());
            System.out.println("poolSize：" + tpe.getPoolSize());
            Thread.sleep(3000);
        }
    }

    /**
     * The default thread factory
     */
    static class MonitorThreadPoolFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MonitorThreadPoolFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "pool-thread-monitor";
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread t = new Thread(group,
                    runnable,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}


