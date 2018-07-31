package com.hexin.sell.service;

import com.hexin.sell.dto.OrderDto;

/**
 * 买家
 * @author hexin
 * @createDate 2018年07月31日 14:09:00
 */
public interface IBuyerServiceImpl {

    OrderDto findOneOrder(String openid, String orderId);

    OrderDto cancelOrder(String openid, String orderId);

}


