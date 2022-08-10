package com.demo.factory.factorymethod;

import com.demo.factory.InputUtil;
import com.demo.factory.bean.Pizza;

/**
 * 工厂方法模式，定义一个创建对象的抽象方法由具体工厂实现
 */
public abstract class FactoryMethod {


    public FactoryMethod() {
        Pizza pizza;
        do {
            String orderType = InputUtil.getType();
            pizza = this.getPizza(orderType);
            if (pizza != null) {
                System.out.println("工厂方法模式");
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            } else {
                System.out.println("不存在的披萨类型");
                break;
            }
        } while (true);
    }

    protected abstract Pizza getPizza(String orderType);
}
