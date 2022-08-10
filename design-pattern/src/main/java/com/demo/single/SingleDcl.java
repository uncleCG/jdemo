package com.demo.single;

/**
 * 线程安全，延迟加载，效率较高，推荐使用
 */
public class SingleDcl {

    /**
     * volatile 是关键
     */
    private static volatile SingleDcl instance;

    private SingleDcl() {}

    public static SingleDcl getInstance() {
        if (instance == null) {
            synchronized (SingleDcl.class) {
                if (instance == null) {
                    instance = new SingleDcl();
                }
            }
        }
        return instance;
    }

    public void sayHello() {
        System.out.println("双重检查锁实现的单例");
    }
}
