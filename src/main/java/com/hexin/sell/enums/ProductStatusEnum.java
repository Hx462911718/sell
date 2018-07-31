package com.hexin.sell.enums;

import lombok.Getter;

/**
 * 商品状态枚举
 * @author hexin
 * @createDate 2018年07月27日 15:35:00
 */
@Getter
public enum ProductStatusEnum {
    UP(0,"上架商品"),
    DOWN(1,"下架商品")
    ;

    private Integer code;
    private  String  desc;

    ProductStatusEnum(Integer code,String desc) {
        this.code = code;
        this.desc = desc;
    }
}
