package com.demo;

/**
 * @author zhangchangyong
 * @date 2020-09-23 09:37
 */
public class InheritTest {

    /**
     * 静态代码块先于非静态代码块执行，静态代码块只在类被加载时执行一次；
     * 非静态代码块先于构造方法执行，每次创建对象非静态代码块和构造方法都会执行一次；
     * 继承关系下静态代码块、非静态代码块、构造方法执行顺序如下：
     * 父类静态代码块
     * 子类静态代码块
     * 父类非静态代码块
     * 父类构造方法
     * 子类非静态代码块
     * 子类构造方法
     * @param args
     */
    public static void main(String[] args) {
        Son son = new Son();
        // 继承中成员变量的关系：当父类与子类成员变量名称一样时。
        // 在子类中访问一个变量的查找顺序：
        // 子类方法的局部范围 > 子类成员 > 父类成员 > 找不到报错
        System.out.println(son.name);
        son.eat();

        System.out.println("=====================");
        Parent parent = new Parent();
        System.out.println(parent.name);
        parent.eat();
        parent.play();

        System.out.println("=====================");
        new Son();
    }

}

class Parent {
    static {
        System.out.println("父类静态代码块");
    }

    {
        System.out.println("父类普通代码块");
    }

    public Parent() {
        System.out.println("父类构造方法");
    }

    public String name = "parentName";

    void eat() {
        System.out.println("parent eat");
    }

    void play() {
        System.out.println("parent play");
    }
}

class Son extends Parent {
    static {
        System.out.println("子类静态代码块");
    }

    {
        System.out.println("子类普通代码块");
    }

    public Son() {
        System.out.println("子类构造方法");
    }

    protected String name = "sonName";

    @Override
    void eat() {
        System.out.println("son eat");
    }
}