package com.demo.factory.bean;

public class GreekPizza extends Pizza {

    public GreekPizza(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("准备希腊披萨原材料");
    }
}
