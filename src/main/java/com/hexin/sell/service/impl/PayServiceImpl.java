package com.hexin.sell.service.impl;

import com.hexin.sell.config.WeChatPayConfig;
import com.hexin.sell.dto.OrderDto;
import com.hexin.sell.enums.ResultEnum;
import com.hexin.sell.exception.SellException;
import com.hexin.sell.service.IOrderService;
import com.hexin.sell.service.IPayService;
import com.hexin.sell.util.JsonUtil;
import com.hexin.sell.util.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hexin
 * @createDate 2018年08月01日 11:03:00
 */

@Service
@Slf4j
public class PayServiceImpl implements IPayService {

    private final static String ORDER_NAME = "微信点餐支付系统";
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private IOrderService orderService;

    @Override
    public PayResponse create(OrderDto orderDto) {

        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        //发起支付
        log.info("【微信支付】发起支付request={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付response={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyDate) {
        //1.验证签名
        //2.支付状态
        //3.支付的金额
        //4.支付人
        PayResponse payResponse = bestPayService.asyncNotify(notifyDate);
        log.info("【微信支付】异步通知response={}", JsonUtil.toJson(payResponse));

        //查询订单
        OrderDto orderDto = orderService.findOne(payResponse.getOrderId());
        if (orderDto == null) {
            log.error("【微信支付】异步通知,订单不存在orderId={}", payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致
        if (!MathUtil.equals(orderDto.getOrderAmount().doubleValue(),payResponse.getOrderAmount())) {
            log.error("【微信支付】异步通知, 订单金额不一致, orderId={}, 微信通知金额={}, 系统金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDto.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        //修改订单支付状态
        orderService.paid(orderDto);

        return payResponse;
    }
}
