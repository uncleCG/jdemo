package com.demo.killifelse.v1.service.impl;

import com.demo.killifelse.v1.context.OrderHandlerContext;
import com.demo.killifelse.dto.OrderDTO;
import com.demo.killifelse.v1.handler.AbstractOrderHandler;
import com.demo.killifelse.v1.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangchangyong
 * @date 2020-07-04 12:20
 */
@Service
public class OrderServiceHandlerImpl implements IOrderService {

    @Autowired
    private OrderHandlerContext handlerContext;

    @Override
    public String handle(OrderDTO orderDTO) {
        AbstractOrderHandler handler = handlerContext.getHandler(orderDTO.getType());
        return handler.handle(orderDTO);
    }
}
