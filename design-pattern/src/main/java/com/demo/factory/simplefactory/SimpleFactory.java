package com.demo.factory.simplefactory;

import com.demo.factory.bean.CheesePizza;
import com.demo.factory.bean.GreekPizza;
import com.demo.factory.bean.Pizza;

public class SimpleFactory {
    public Pizza getPizza(String orderType) {
        Pizza pizza = null;
        if ("greek".equalsIgnoreCase(orderType)) {
            pizza = new GreekPizza("Greek");
        } else if ("cheese".equalsIgnoreCase(orderType)) {
            pizza = new CheesePizza("Cheese");
        }
        return pizza;
    }

    public static Pizza getPizza2(String orderType) {
        Pizza pizza = null;
        if ("greek".equalsIgnoreCase(orderType)) {
            pizza = new GreekPizza("Greek");
        } else if ("cheese".equalsIgnoreCase(orderType)) {
            pizza = new CheesePizza("Cheese");
        }
        return pizza;
    }
}
