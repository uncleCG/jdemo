package com.demo.factory.abstractfactory;

import com.demo.factory.bean.LdCheesePizza;
import com.demo.factory.bean.LdGreekPizza;
import com.demo.factory.bean.Pizza;
import com.demo.factory.factorymethod.FactoryMethod;

public class LdPizzaFactory implements AbstractFactory {
    @Override
    public Pizza getPizza(String orderType) {
        Pizza pizza = null;
        if ("greek".equalsIgnoreCase(orderType)) {
            pizza = new LdGreekPizza("Greek");
        } else if ("cheese".equalsIgnoreCase(orderType)) {
            pizza = new LdCheesePizza("Cheese");
        }
        return pizza;
    }
}
