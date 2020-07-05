package com.demo.killifelse;

import com.demo.killifelse.dto.OrderDTO;
import com.demo.killifelse.v1.service.IOrderService;
import com.demo.killifelse.v2.handler.enums.OrderTypeEnum;
import com.demo.killifelse.v2.service.OrderServiceV2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KillIfElseApplicationTests {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private OrderServiceV2 orderServiceV2;

    @Test
    void contextLoads() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setType("1");
        System.out.println(orderService.handle(orderDTO));
        orderDTO.setType("2");
        System.out.println(orderService.handle(orderDTO));
        orderDTO.setType("3");
        System.out.println(orderService.handle(orderDTO));

        OrderDTO orderDTOV2 = new OrderDTO();
        orderDTOV2.setType(OrderTypeEnum.NORMAL.name());
        System.out.println(orderServiceV2.handle(orderDTOV2));
        orderDTOV2.setType(OrderTypeEnum.GROUP.name());
        System.out.println(orderServiceV2.handle(orderDTOV2));
        orderDTOV2.setType(OrderTypeEnum.PROMOTE.name());
        System.out.println(orderServiceV2.handle(orderDTOV2));
    }
}
