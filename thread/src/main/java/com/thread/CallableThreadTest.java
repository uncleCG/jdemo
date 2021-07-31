package com.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zhangchangyong
 * @date 2020-08-12 10:13
 */
public class CallableThreadTest implements Callable<IntegerTest> {

    public static void main(String[] args) {
        CallableThreadTest callableThreadTest = new CallableThreadTest();
        FutureTask<IntegerTest> futureTask = new FutureTask<>(callableThreadTest);
        for (int i = 0; i < 100; i++) {
            System.out.println(ThreadLocalRandom.current().nextLong(10,12) + "@" + Thread.currentThread().getName() + " 的循环变量i的值" + i);
            if (i == 20) {
                new Thread(futureTask, "有返回值的线程").start();
            }
        }
        try {
            System.out.println("子线程的返回值：" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IntegerTest call() throws Exception {
        int i = 0;
        for (; i < 100; i++) {
            System.out.println(ThreadLocalRandom.current().nextInt(0,2)  + "@" + Thread.currentThread().getName() + " " + i);
        }
        return null;
    }
}
