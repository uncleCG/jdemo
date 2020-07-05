package com.demo.killifelse.v1.handler;

import com.demo.killifelse.v1.annotation.OrderType;
import com.demo.killifelse.dto.OrderDTO;
import org.springframework.stereotype.Component;

/**
 * 促销订单处理器
 *
 * @author zhangchangyong
 * @date 2020-07-04 11:28
 */
@Component
@OrderType("3")
public class PromoteOrderHandler extends AbstractOrderHandler {
    @Override
    public String handle(OrderDTO orderDTO) {
        return "处理促销订单v1";
    }
}
