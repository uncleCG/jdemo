package com.demo.killifelse.v1.handler;

import com.demo.killifelse.v1.annotation.OrderType;
import com.demo.killifelse.dto.OrderDTO;
import org.springframework.stereotype.Component;

/**
 * 团购订单处理器
 *
 * @author zhangchangyong
 * @date 2020-07-04 11:27
 */
@Component
@OrderType("2")
public class GroupOrderHandler extends AbstractOrderHandler {
    @Override
    public String handle(OrderDTO orderDTO) {
        return "处理团购订单v1";
    }
}
