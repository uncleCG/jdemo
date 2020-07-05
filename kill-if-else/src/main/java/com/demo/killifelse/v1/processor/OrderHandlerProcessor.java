package com.demo.killifelse.v1.processor;

import com.demo.killifelse.v1.annotation.OrderType;
import com.demo.killifelse.v1.context.OrderHandlerContext;
import com.demo.killifelse.v1.util.ClassScaner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author zhangchangyong
 * @date 2020-07-04 11:32
 */
@Component
public class OrderHandlerProcessor implements BeanFactoryPostProcessor {

    private static final String HANDLER_PACKAGE = "com.demo.killifelse.v1.handler";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        HashMap<String, Class<?>> handlerMap = new HashMap<>(8);
        // 扫面指定包获取处理器Class对象
        ClassScaner.scan(HANDLER_PACKAGE, OrderType.class).forEach(handlerClazz -> {
            String orderType = handlerClazz.getAnnotation(OrderType.class).value();
            handlerMap.put(orderType, handlerClazz);
        });
        // 初始化HandlerContext并将其注册到spring容器中
        OrderHandlerContext orderHandlerContext = new OrderHandlerContext(handlerMap);
        configurableListableBeanFactory.registerSingleton(OrderHandlerContext.class.getName(), orderHandlerContext);
    }
}
