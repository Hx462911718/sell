package com.hexin.sell.controller;

import com.hexin.sell.dto.OrderDto;
import com.hexin.sell.enums.ResultEnum;
import com.hexin.sell.exception.SellException;
import com.hexin.sell.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hexin
 * @createDate 2018年08月01日 10:57:00
 */
@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private IOrderService orderService;

    @GetMapping
    public void create(@RequestParam("orderId") String orderId,@RequestParam("returnUrl") String returnUrl){

        //1.查询订单
       OrderDto orderDto =  orderService.findOne(orderId);
       if (orderDto == null){
           throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
       }
       //2.支付
    }
}
