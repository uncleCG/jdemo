package com.demo.factory.bean;

import com.demo.factory.bean.Pizza;

public class BjCheesePizza extends Pizza {

    public BjCheesePizza(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("准备BJ奶油披萨原材料");
    }
}
