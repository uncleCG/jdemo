package com.demo.killifelse.v1.service;

import com.demo.killifelse.dto.OrderDTO;

/**
 * @author zhangchangyong
 * @date 2020-07-04 10:59
 */
public interface IOrderService {

    /**
     * 根据订单的不同类型作出不同的处理
     *
     * @param orderDTO
     * @return
     */
    String handle(OrderDTO orderDTO);
}
