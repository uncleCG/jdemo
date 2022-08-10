package com.demo.single;

/**
 * 线程安全，利用静态内部类特点实现延迟加载，效率高，推荐使用。
 */
public class SingleStaticInnerClass {

    private SingleStaticInnerClass(){}

    /**
     * 静态内部类
     * 外部类加载的时候，内部类不会被加载
     */
    private static class Singleton {
        private static final SingleStaticInnerClass INSTANCE = new SingleStaticInnerClass();

    }

    public static SingleStaticInnerClass getInstance() {
        return Singleton.INSTANCE;
    }
    public void sayHello() {
        System.out.println("静态内部类实现的单例");
    }
}
