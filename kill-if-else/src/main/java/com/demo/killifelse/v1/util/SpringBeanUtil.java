package com.demo.killifelse.v1.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhangchangyong
 * @date 2020-07-04 11:48
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext = appContext;
    }

    /**
     * 根据Class类型获取bean对象
     *
     * @param clazz bean 类型
     * @param <T>   返回的Bean类型
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据名称获取bean对象
     *
     * @param beanName bean名称
     * @return
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
