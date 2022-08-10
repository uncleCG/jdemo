package com.demo.factory.factorymethod;

import com.demo.factory.bean.Pizza;
import com.demo.factory.bean.BjCheesePizza;
import com.demo.factory.bean.BjGreekPizza;

public class BjPizzaFactory extends FactoryMethod{
    @Override
    protected Pizza getPizza(String orderType) {
        Pizza pizza = null;
        if ("greek".equalsIgnoreCase(orderType)) {
            pizza = new BjGreekPizza("Greek");
        } else if ("cheese".equalsIgnoreCase(orderType)) {
            pizza = new BjCheesePizza("Cheese");
        }
        return pizza;
    }
}
