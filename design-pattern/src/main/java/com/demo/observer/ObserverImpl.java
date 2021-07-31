package com.demo.observer;

/**
 * 具体观察者
 *
 * @author zhangchangyong
 * @date 2021-07-28 11:19
 */
public class ObserverImpl implements Observer {

    /**
     * 具体观察者的属性
     */
    private int myState;

    @Override
    public void update(int state) {
        myState = state;
        System.out.println("收到消息,myState值改为：" + state);
    }

    public int getMyState() {
        return myState;
    }
}