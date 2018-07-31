package com.hexin.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品信息vo
 * @author hexin
 * @createDate 2018年07月27日 16:15:00
 */
@Data
public class ProductInfoVo implements Serializable {

        private static final long serialVersionUID = 4177439763246797991L;

        @JsonProperty("id")
        private String productId;
        @JsonProperty("name")
        private String productName;
        @JsonProperty("price")
        private BigDecimal productPrice;
        @JsonProperty("description")
        private String productDescription;
        @JsonProperty("icon")
        private String productIcon;
}
