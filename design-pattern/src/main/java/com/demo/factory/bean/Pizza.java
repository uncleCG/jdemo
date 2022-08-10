package com.demo.factory.bean;

public abstract class Pizza {

    protected String name;

    public Pizza(String name) {
        this.name = name;
    }

    public abstract void prepare();

    public void bake() {
        System.out.println(name + " 制作中...");
    }

    public void cut() {
        System.out.println(name + " 分割中...");
    }

    public void box() {
        System.out.println(name + " 装箱中...");
    }

}
