package com.demo.observer;

/**
 * 观察者模式
 * 1.什么是观察者模式
 *
 *     先讲什么是行为性模型，行为型模式关注的是系统中对象之间的相互交互，解决系统在运行时对象之间的相互通信和协作，进一步明确对象的职责。
 *     观察者模式，是一种行为性模型，又叫发布-订阅模式，他定义对象之间一对多的依赖关系，使得当一个对象改变状态，则所有依赖于它的对象都会得到通知并自动更新。
 *
 * 2.模式的职责
 *
 *     观察者模式主要用于1对N的通知。当一个对象的状态变化时，他需要及时告知一系列对象，令他们做出相应。
 *
 * 实现有两种方式：
 *
 *     推：每次都会把通知以广播的方式发送给所有观察者，所有的观察者只能被动接收。
 *     拉：观察者只要知道有情况即可，至于什么时候获取内容，获取什么内容，都可以自主决定。
 *
 * 3.观察者模式应用场景
 *
 *     关联行为场景，需要注意的是，关联行为是可拆分的，而不是“组合”关系。事件多级触发场景。
 *     跨系统的消息交换场景，如消息队列、事件总线的处理机制。
 *
 * @author zhangchangyong
 * @date 2021-07-28 11:40
 */
public class Main {

    public static void main(String[] args) {
        // 目标对象（被观察者）
        Observable observable = new Observable();
        // 创建多个观察者
        ObserverImpl obs1 = new ObserverImpl();
        ObserverImpl obs2 = new ObserverImpl();
        ObserverImpl obs3 = new ObserverImpl();
        // 注册到被观察者的观察队列中
        observable.registerObserver(obs1);
        observable.registerObserver(obs2);
        observable.registerObserver(obs3);
        // 改变State状态
        observable.setState(300);
        System.out.println("obs1观察者的MyState状态值为：" + obs1.getMyState());
        System.out.println("obs2观察者的MyState状态值为：" + obs2.getMyState());
        System.out.println("obs3观察者的MyState状态值为：" + obs3.getMyState());
        // 改变State状态
        observable.setState(400);
        System.out.println("obs1观察者的MyState状态值为：" + obs1.getMyState());
        System.out.println("obs2观察者的MyState状态值为：" + obs2.getMyState());
        System.out.println("obs3观察者的MyState状态值为：" + obs3.getMyState());
    }
}
