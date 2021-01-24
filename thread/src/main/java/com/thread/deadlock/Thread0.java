package com.thread.deadlock;

public class Thread0 extends Thread {
    private DeadLock dl;

    public Thread0(DeadLock dl) {
        this.dl = dl;
    }

    @Override
    public void run() {
        try {
            dl.firstLockObj1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}