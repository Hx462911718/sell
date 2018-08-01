package com.hexin.sell.controller;

import com.hexin.sell.dto.OrderDto;
import com.hexin.sell.enums.ResultEnum;
import com.hexin.sell.exception.SellException;
import com.hexin.sell.service.IOrderService;
import com.hexin.sell.service.IPayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.POST;
import java.util.Map;

/**
 * @author hexin
 * @createDate 2018年08月01日 10:57:00
 */
@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IPayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl, Map<String, Object> map) {

        //1.查询订单
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.支付
        PayResponse payResponse = payService.create(orderDto);
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("/pay/create", map);
    }

    //接收微信异步通知
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyDate) {
        payService.notify(notifyDate);
        //返回通知结果给微信
        return new ModelAndView("/pay/success");
    }
}
