package com.hexin.sell.controller;

import com.hexin.sell.Common.ResultVO;
import com.hexin.sell.Common.ResultVOUtil;
import com.hexin.sell.converter.OrderForm2OrderDtoConverter;
import com.hexin.sell.dto.OrderDto;
import com.hexin.sell.enums.ResultEnum;
import com.hexin.sell.exception.SellException;
import com.hexin.sell.form.OrderForm;
import com.hexin.sell.service.IBuyerService;
import com.hexin.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hexin
 * @createDate 2018年07月31日 10:13:00
 */
@Controller
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IBuyerService buyerService;

    //创建订单
    @RequestMapping("/create")
    @ResponseBody
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        //校验表单是否有错
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数有问题,orderForm={}",orderForm);
            throw  new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDto orderDto = OrderForm2OrderDtoConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw  new SellException(ResultEnum.CART_EMPTY);
        }
       OrderDto dto =  iOrderService.createOrder(orderDto);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",dto.getOrderId());
        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    @ResponseBody
    public  ResultVO  list(@RequestParam(value = "openid") String openid,
                           @RequestParam(value = "page",defaultValue = "0") Integer page,
                           @RequestParam(value = "size",defaultValue = "2") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Pageable  pageable = new PageRequest(page,size);
        Page<OrderDto> orderDtos = iOrderService.findList(openid,pageable);
        return ResultVOUtil.success(orderDtos.getContent());
    }
    //订单详情
    @RequestMapping("/detail")
    @ResponseBody
    public  ResultVO<OrderDto>  detail(@RequestParam(value = "openid") String openid,
                           @RequestParam(value = "orderId") String orderId){

        if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
            log.error("【查询订单详情】openid为空或orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDto orderDto = buyerService.findOneOrder(openid,orderId);
        return ResultVOUtil.success(orderDto);
    }

    //取消订单
    @RequestMapping("/cancel")
    @ResponseBody
    public  ResultVO cancel(@RequestParam(value = "openid") String openid,
                                       @RequestParam(value = "orderId") String orderId){

        if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
            log.error("【查询订单详情】openid为空或orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
