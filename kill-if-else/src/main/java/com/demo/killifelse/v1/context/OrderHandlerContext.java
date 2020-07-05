package com.demo.killifelse.v1.context;

import com.demo.killifelse.v1.handler.AbstractOrderHandler;
import com.demo.killifelse.v1.util.SpringBeanUtil;

import java.util.Map;

/**
 * @author zhangchangyong
 * @date 2020-07-04 11:42
 */
public class OrderHandlerContext {

    private Map<String, Class<?>> handlerMap;

    public OrderHandlerContext(Map<String, Class<?>> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractOrderHandler getHandler(String type) {
        Class<?> handlerClazz = handlerMap.get(type);
        if (handlerClazz == null) {
            throw new IllegalArgumentException("not found handler for type：" + type);
        }
        return (AbstractOrderHandler) SpringBeanUtil.getBean(handlerClazz);
    }
}
