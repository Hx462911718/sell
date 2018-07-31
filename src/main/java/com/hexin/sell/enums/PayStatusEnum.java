package com.hexin.sell.enums;

import lombok.Getter;

/**
 * 支付状态枚举
 * @author hexin
 * @createDate 2018年07月27日 15:35:00
 */
@Getter
public enum PayStatusEnum {
    WAIT(0,"未支付"),
    SUCCESS(1,"支付成功")
    ;

    private Integer code;
    private  String  desc;

    PayStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
