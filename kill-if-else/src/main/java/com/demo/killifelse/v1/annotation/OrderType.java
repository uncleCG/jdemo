package com.demo.killifelse.v1.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 订单类型枚举
 *
 * @author zhangchangyong
 * @date 2020-07-04 11:10
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderType {
    /**
     * 订单类型
     *
     * @return
     */
    String value();
}
