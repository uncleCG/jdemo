package com.demo.factory;

import com.demo.factory.abstractfactory.LdPizzaFactory;
import com.demo.factory.abstractfactory.order.PizzaOrder;

/**
 *
 * 三种工厂模式的区别参考：
 * https://www.cnblogs.com/yssjun/p/11102162.html
 *
 * 简单工厂模式，又称静态工厂模式，只能生产一种产品
 * 区别：使用方不用创建工厂对象，直接调用对应工厂方法
 *
 * 工厂方法模式，定义一个创建对象的抽象方法，有子类实现具体的创建对象逻辑
 *
 * 抽象工厂模式：
 * 定义一个interface用于创建相关或有依赖关系的对象簇，而无需指明具体的类；
 * 将简单工厂模式和工厂方法模式进行整合；
 * 抽象工厂是对简单工厂的进一步抽象，分为抽象工厂和工厂子类，将简单工厂变成了工厂簇；
 */
public class PizzaStoreMain {
    public static void main(String[] args) {
        // 传统模式
        // new OrderPizza();

        // 简单工厂模式
        // new OrderPizza(new SimpleFactory());
        // 简单工厂静态方法模式
        // new OrderPizza2();

        // 工厂方法
        // new BjPizzaFactory();
        // new LdPizzaFactory();

        // 抽象工厂模式
        // new PizzaOrder(new BjPizzaFactory());
        new PizzaOrder(new LdPizzaFactory());
    }
}
