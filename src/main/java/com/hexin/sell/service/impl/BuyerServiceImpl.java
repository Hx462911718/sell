package com.hexin.sell.service.impl;

import com.hexin.sell.dto.OrderDto;
import com.hexin.sell.enums.ResultEnum;
import com.hexin.sell.exception.SellException;
import com.hexin.sell.service.IBuyerService;
import com.hexin.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hexin
 * @createDate 2018年07月31日 14:11:00
 */
@Slf4j
@Service
public class BuyerServiceImpl implements IBuyerService {
    @Autowired
    private IOrderService orderService;

    @Override
    public OrderDto findOneOrder(String openid, String orderId) {


        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDto cancelOrder(String openid, String orderId) {
        OrderDto orderDto = checkOrderOwner(openid,orderId);
        if (orderDto == null){
            log.error("【取消订单】订单不存在");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        return orderService.cancel(orderDto);
    }


    private OrderDto checkOrderOwner(String openid, String orderId){

        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null){
            return  null;
        }
        if(!orderDto.getBuyerOpenid().equals(openid)){
            log.error("【查询订单】订单openid不存在,openid={},orderDto={}",openid,orderDto.getBuyerOpenid());
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }
}
