package com.demo.killifelse.v2.handler;

import com.demo.killifelse.dto.OrderDTO;

/**
 * @author zhangchangyong
 * @date 2020-07-04 18:59
 */
public interface OrderHandler {

    /**
     * 订单处理逻辑
     * @param dto
     * @return
     */
    String handle(OrderDTO dto);

    /**
     * 当前handler可以处理的订单类型
     * @return
     */
    String orderType();
}
