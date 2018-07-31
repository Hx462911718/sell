package com.hexin.sell.dto;

import lombok.Getter;

/**
 * 购物车
 * @author hexin
 * @createDate 2018年07月30日 17:05:00
 */
@Getter
public class CartDto {

    //商品id
    private  String productId;

    //数量
    private  Integer productQuantity;

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
