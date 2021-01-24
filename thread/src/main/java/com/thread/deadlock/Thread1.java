package com.thread.deadlock;

public class Thread1 extends Thread {
    private DeadLock dl;

    public Thread1(DeadLock dl)
    {
        this.dl = dl;
    }

    @Override
    public void run()
    {
        try
        {
            dl.firstLockObj2();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}