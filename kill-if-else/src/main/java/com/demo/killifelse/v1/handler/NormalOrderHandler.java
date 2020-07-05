package com.demo.killifelse.v1.handler;

import com.demo.killifelse.v1.annotation.OrderType;
import com.demo.killifelse.dto.OrderDTO;
import org.springframework.stereotype.Component;

/**
 * 普通订单处理器
 *
 * @author zhangchangyong
 * @date 2020-07-04 11:19
 */
@Component
@OrderType("1")
public class NormalOrderHandler extends AbstractOrderHandler {
    @Override
    public String handle(OrderDTO orderDTO) {
        return "处理普通订单v1";
    }
}
