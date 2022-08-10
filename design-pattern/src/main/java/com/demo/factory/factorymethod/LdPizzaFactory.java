package com.demo.factory.factorymethod;

import com.demo.factory.bean.Pizza;
import com.demo.factory.bean.LdCheesePizza;
import com.demo.factory.bean.LdGreekPizza;

public class LdPizzaFactory extends FactoryMethod{
    @Override
    protected Pizza getPizza(String orderType) {
        Pizza pizza = null;
        if ("greek".equalsIgnoreCase(orderType)) {
            pizza = new LdGreekPizza("Greek");
        } else if ("cheese".equalsIgnoreCase(orderType)) {
            pizza = new LdCheesePizza("Cheese");
        }
        return pizza;
    }
}
