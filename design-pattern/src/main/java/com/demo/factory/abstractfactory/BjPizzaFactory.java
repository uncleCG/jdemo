package com.demo.factory.abstractfactory;

import com.demo.factory.bean.BjCheesePizza;
import com.demo.factory.bean.BjGreekPizza;
import com.demo.factory.bean.Pizza;

public class BjPizzaFactory implements AbstractFactory {
    @Override
    public Pizza getPizza(String orderType) {
        Pizza pizza = null;
        if ("greek".equalsIgnoreCase(orderType)) {
            pizza = new BjGreekPizza("Greek");
        } else if ("cheese".equalsIgnoreCase(orderType)) {
            pizza = new BjCheesePizza("Cheese");
        }
        return pizza;
    }
}
