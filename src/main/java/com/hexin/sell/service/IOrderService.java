package com.hexin.sell.service;

import com.hexin.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author hexin
 * @createDate 2018年07月30日 15:57:00
 */
public interface IOrderService {

    //1.创建订单
    OrderDto createOrder(OrderDto orderDto);

    //2.查询订单
    OrderDto findOne(String orderId);

    //3.查询订单列表
    Page<OrderDto> findList(String buyerOpenid, Pageable pageable);

    //4.取消订单
    OrderDto cancel(OrderDto orderDto);

    //5.完结订单
    OrderDto finish(OrderDto orderDto);

    //6.支付订单
    OrderDto paid(OrderDto orderDto);
}
