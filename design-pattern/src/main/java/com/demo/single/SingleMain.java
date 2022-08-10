package com.demo.single;

/**
 * 思路：
 * 1，不让其他类建立该类对象，因为其他类建立对象会有很多。无法控制。
 * 2，在该类中，建立一个本类对象。
 * 3，对外提供方法获取该对象即可。
 *
 * 步骤：
 * 1，私有化构造函数，不让其他类建立对象初始化。
 * 2，建立一个本类对象，并私有和静态。
 * 3，对外提供一个静态方法让其他类可以获取该对象。
 *
 *  单例的8种写法：
 * 	https://www.bilibili.com/video/BV1G4411c7N4?p=38&spm_id_from=pageDriver&vd_source=df9337586402230e96e8f8b3b27ad331
 * 	饿汉式两种：
 * 		1、静态常量方式；
 * 		2、静态代码块方式；
 * 	懒汉式三种：
 * 		1、线程不安全方式；
 * 		2、synchronized方法方式；
 * 		3、synchronized代码块方式；
 * 	dcl双重检查锁方式
 * 		线程安全，延迟加载，效率较高，推荐使用
 * 	静态内部类
 * 		线程安全，利用静态内部类特点实现延迟加载，效率高，推荐使用。
 * 	枚举
 * 		高效编程作者推荐使用
 *
 * JDK源码中Runtime类使用饿汉式静态常量方式返回Runtime对象
 * private static Runtime currentRuntime = new Runtime();
 *
 */
public class SingleMain {

    public static void main(String[] args) {
        // 枚举方式实现单例
        SingleEnum singleEnum1 = SingleEnum.INSTANCE;
        SingleEnum singleEnum2 = SingleEnum.INSTANCE;
        System.out.println(singleEnum1.hashCode() == singleEnum2.hashCode());
        singleEnum1.sayHello();
        // 静态内部类实现单例
        SingleStaticInnerClass singleStaticInnerClass1 = SingleStaticInnerClass.getInstance();
        SingleStaticInnerClass singleStaticInnerClass2 = SingleStaticInnerClass.getInstance();
        System.out.println(singleStaticInnerClass1.hashCode() == singleStaticInnerClass2.hashCode());
        singleStaticInnerClass1.sayHello();

        // 双重检查锁实现单例
        SingleDcl singleDcl1 = SingleDcl.getInstance();
        SingleDcl singleDcl2 = SingleDcl.getInstance();
        System.out.println(singleDcl1.hashCode() == singleDcl2.hashCode());
        singleDcl1.sayHello();
    }
}
