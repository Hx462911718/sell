package com.hexin.sell.enums;

import lombok.Getter;

/**
 * 订单状态枚举
 * @author hexin
 * @createDate 2018年07月30日 14:47:00
 */
@Getter
public enum OrderStatus {
    NEW(0,"新订单"),
    FINSHED(1,"订单完成"),
    CANCEL(2,"取消订单")

    ;

    private Integer code;
    private String desc;

    OrderStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
