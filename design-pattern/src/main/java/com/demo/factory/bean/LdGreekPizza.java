package com.demo.factory.bean;

import com.demo.factory.bean.Pizza;

public class LdGreekPizza extends Pizza {

    public LdGreekPizza(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("准备LD希腊披萨原材料");
    }
}
