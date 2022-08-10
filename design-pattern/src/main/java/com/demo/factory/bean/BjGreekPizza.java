package com.demo.factory.bean;

import com.demo.factory.bean.Pizza;

public class BjGreekPizza extends Pizza {

    public BjGreekPizza(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("准备BJ希腊披萨原材料");
    }
}
