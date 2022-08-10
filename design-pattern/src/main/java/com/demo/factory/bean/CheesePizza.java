package com.demo.factory.bean;

public class CheesePizza extends Pizza {

    public CheesePizza(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("准备奶油披萨原材料");
    }

}
