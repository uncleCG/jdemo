package com.demo.factory.abstractfactory;

import com.demo.factory.bean.Pizza;

/**
 * 抽象工厂
 * 接口定义工厂方法，具体
 */
public interface AbstractFactory {

    Pizza getPizza(String orderType);
}
