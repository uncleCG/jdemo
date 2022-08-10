package com.demo.single;

/**
 * 高效，高效编程作者推荐使用
 */
public enum SingleEnum {

    /**
     * 单例对象
     */
    INSTANCE;

    void sayHello() {
        System.out.println("枚举实现的单例模式");
    }
}
