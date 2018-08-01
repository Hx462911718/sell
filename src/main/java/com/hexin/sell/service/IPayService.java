package com.hexin.sell.service;

import com.hexin.sell.dto.OrderDto;

/**
 * @author hexin
 * @createDate 2018年08月01日 11:03:00
 */
public interface IPayService {

    void create (OrderDto orderDto);
}
