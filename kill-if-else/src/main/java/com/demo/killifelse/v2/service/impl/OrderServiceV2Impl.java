package com.demo.killifelse.v2.service.impl;

import com.demo.killifelse.dto.OrderDTO;
import com.demo.killifelse.v2.handler.OrderHandler;
import com.demo.killifelse.v2.service.OrderServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhangchangyong
 * @date 2020-07-04 12:20
 */
@Service
public class OrderServiceV2Impl implements OrderServiceV2 {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String handle(OrderDTO orderDTO) {
        String orderType = orderDTO.getType();
        // Spring 有个 Map Cache 根据 class 来缓存 bean Name, 此处无需担忧性能
        Map<String, OrderHandler> beans = applicationContext.getBeansOfType(OrderHandler.class);
        return beans.values().stream()
                .filter(x -> x.orderType().equals(orderType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("not found handler for type: " + orderType))
                .handle(orderDTO);
    }
}
