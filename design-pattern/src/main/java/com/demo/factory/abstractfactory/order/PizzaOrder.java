package com.demo.factory.abstractfactory.order;

import com.demo.factory.abstractfactory.AbstractFactory;
import com.demo.factory.bean.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PizzaOrder {

    public PizzaOrder(AbstractFactory factory) {
        this.setFactory(factory);
    }

    private void setFactory(AbstractFactory factory) {
        Pizza pizza = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        do {
            String orderType ;
            try {
                orderType = bufferedReader.readLine();
                System.out.println("输入的披萨类型：" + orderType);
            } catch (IOException e) {
                orderType = "";
            }
            pizza = factory.getPizza(orderType);
            if (pizza != null) {
                System.out.println("抽象工厂模式");
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            } else {
                System.out.println("不存在的披萨类型");
                break;
            }
        } while (true);
        try {
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
