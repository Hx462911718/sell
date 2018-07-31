package com.hexin.sell.converter;

import com.hexin.sell.dto.OrderDto;
import com.hexin.sell.pojo.OrderMaster;
import com.hexin.sell.util.BeanCopyUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hexin
 * @createDate 2018年07月31日 8:59:00
 */
public class OrderMaster2OrderDtoConverter {

    public  static OrderDto convert(OrderMaster orderMaster){

        OrderDto orderDto  = new OrderDto();
        BeanCopyUtil.copyNotNullProperties(orderMaster,orderDto);
        return orderDto;
    }

    public  static List<OrderDto> convert(List<OrderMaster> orderMasterList){

        return orderMasterList.stream().map(e->convert(e)).collect(Collectors.toList());
    }
}
