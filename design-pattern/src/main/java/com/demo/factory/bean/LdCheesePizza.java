package com.demo.factory.bean;

import com.demo.factory.bean.Pizza;

public class LdCheesePizza extends Pizza {

    public LdCheesePizza(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("准备LD奶油披萨原材料");
    }
}
