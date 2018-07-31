package com.hexin.sell.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: hexin
 * Date: 2018/1/23.
 * Time: 下午 10:48.
 * Explain: 卖家信息
 */
@Data
@Entity
public class SellerInfo {
    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;

}
