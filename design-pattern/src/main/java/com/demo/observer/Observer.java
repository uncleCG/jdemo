package com.demo.observer;

/**
 * 定义抽象观察者，每一个实现该接口的实现类都是具体观察者。
 * 观察者的接口，用来存放观察者共有方法
 *
 * @author zhangchangyong
 * @date 2021-07-28 11:19
 */
public interface Observer {

    /**
     * 观察者方法
     *
     * @param state
     */
    void update(int state);
}
