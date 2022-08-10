package com.demo.factory.simplefactory.order;

import com.demo.factory.InputUtil;
import com.demo.factory.bean.CheesePizza;
import com.demo.factory.bean.GreekPizza;
import com.demo.factory.bean.Pizza;
import com.demo.factory.simplefactory.SimpleFactory;

public class OrderPizza {

    public OrderPizza() {
        Pizza pizza;
        do {
            String orderType = InputUtil.getType();
            if ("greek".equalsIgnoreCase(orderType)) {
                pizza = new GreekPizza("Greek");
            } else if ("cheese".equalsIgnoreCase(orderType)) {
                pizza = new CheesePizza("Cheese");
            } else {
                break;
            }
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        } while (true);
    }

    public OrderPizza(SimpleFactory simpleFactory) {
        Pizza pizza;
        do {
            String orderType = InputUtil.getType();
            pizza = simpleFactory.getPizza(orderType);
            if (pizza != null) {
                System.out.println("简单工厂模式");
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
