package com.demo.killifelse.v2.handler.impl;

import com.demo.killifelse.dto.OrderDTO;
import com.demo.killifelse.v2.handler.OrderHandler;
import com.demo.killifelse.v2.handler.enums.OrderTypeEnum;
import org.springframework.stereotype.Service;

/**
 * @author zhangchangyong
 * @date 2020-07-04 19:10
 */
@Service
public class PromoteOrderHandlerV2 implements OrderHandler {
    @Override
    public String handle(OrderDTO dto) {
        return "处理促销订单v2";
    }

    @Override
    public String orderType() {
        return OrderTypeEnum.PROMOTE.name();
    }
}
