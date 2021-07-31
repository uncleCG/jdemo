package com.demo.observer;

/**
 * 具体被观察者
 *
 * @author zhangchangyong
 * @date 2021-07-28 11:37
 */
public class Observable extends Subject {

    /**
     * 被观察对象的属性
     */
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        // 主题对象(目标对象)值发生改变
        this.notifyAllObserver(state);
    }
}
