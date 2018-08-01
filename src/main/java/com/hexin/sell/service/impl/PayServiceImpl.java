package com.hexin.sell.service.impl;

import com.hexin.sell.config.WeChatPayConfig;
import com.hexin.sell.dto.OrderDto;
import com.hexin.sell.service.IPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hexin
 * @createDate 2018年08月01日 11:03:00
 */

@Service
public class PayServiceImpl implements IPayService {

    private  final  static  String ORDER_NAME = "微信点餐支付系统";
    @Autowired
    private BestPayServiceImpl bestPayService;

    @Override
    public void create(OrderDto orderDto) {

        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        //发起支付
        bestPayService.pay(payRequest);
    }
}
