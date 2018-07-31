package com.hexin.sell.service.impl;

import com.hexin.sell.dto.OrderDto;
import com.hexin.sell.enums.OrderStatus;
import com.hexin.sell.pojo.OrderDetail;
import com.hexin.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author hexin
 * @createDate 2018年07月30日 17:21:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private IOrderService orderService;

    @Test
    public void createOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress("武汉纺织大学");
        orderDto.setBuyerName("hexin");
        orderDto.setBuyerPhone("999999");
        orderDto.setBuyerOpenid("100000");

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("2446c028df714276959fb26e78568bd1");
        o1.setProductQuantity(5);
        orderDetails.add(o1);
        orderDto.setOrderDetailList(orderDetails);
        OrderDto result =  orderService.createOrder(orderDto);
        log.info("创建订单result={}",result);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findList() {
        Pageable pageable = new PageRequest(0,2);
        Assert.assertNotEquals(0,   orderService.findList("100000",pageable).getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDto orderDto = orderService.findOne("1532943515192207631");
        OrderDto orderDtoResult =orderService.cancel(orderDto);
        Assert.assertEquals(OrderStatus.CANCEL.getCode(),orderDto.getOrderStatus());
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}