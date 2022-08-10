package com.demo.factory.simplefactory.order;

import com.demo.factory.InputUtil;
import com.demo.factory.bean.Pizza;
import com.demo.factory.simplefactory.SimpleFactory;

public class OrderPizza2 {
    public OrderPizza2() {
        Pizza pizza;
        do {
            String orderType = InputUtil.getType();
            pizza = SimpleFactory.getPizza2(orderType);
            if (pizza != null) {
                System.out.println("简单工厂静态方法模式");
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
}
