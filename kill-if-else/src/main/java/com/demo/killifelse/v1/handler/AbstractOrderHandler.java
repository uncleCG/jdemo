package com.demo.killifelse.v1.handler;

import com.demo.killifelse.dto.OrderDTO;

/**
 * 订单处理器抽象类
 *
 * @author zhangchangyong
 * @date 2020-07-04 11:14
 */
public abstract class AbstractOrderHandler {

    /**
     * 处理订单逻辑
     * @param orderDTO
     * @return
     */
    abstract public String handle(OrderDTO orderDTO);
}
