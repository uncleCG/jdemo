package com.thread.deadlock;

/**
 * @author zhangchangyong
 * @date 2020-08-11 17:09
 */
public class DeadLock {
    private final Object lockObj1 = new Object();
    private final Object lockObj2 = new Object();

    /**
     * 先获取lockObj1
     * @throws Exception
     */
    public void firstLockObj1() throws Exception {
        synchronized (lockObj1) {
            Thread.sleep(1000);
            synchronized (lockObj2) {
                System.out.println("firstLockObj1方法获取lockObj2成功");
            }
        }
    }

    /**
     * 先获取lockObj2
     * @throws Exception
     */
    public void firstLockObj2() throws Exception {
        synchronized (lockObj2) {
            Thread.sleep(1000);
            synchronized (lockObj1) {
                System.out.println("firstLockObj2方法获取lockObj1成功");
            }
        }
    }

    public static void main(String[] args) {
        DeadLock dl = new DeadLock();
        Thread0 t0 = new Thread0(dl);
        Thread1 t1 = new Thread1(dl);
        t0.start();
        t1.start();

        while (true) {
            ;
        }
    }
}
