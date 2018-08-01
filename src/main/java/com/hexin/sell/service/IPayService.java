package com.hexin.sell.service;

import com.hexin.sell.dto.OrderDto;
import com.lly835.bestpay.model.PayResponse;

/**
 * @author hexin
 * @createDate 2018年08月01日 11:03:00
 */
public interface IPayService {

    PayResponse create (OrderDto orderDto);

    PayResponse  notify(String notifyDate);
}
