package com.hexin.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类vo
 * @author hexin
 * @createDate 2018年07月27日 16:13:00
 */
@Data
public class ProductVo implements Serializable {
    private static final long serialVersionUID = 1397557650534605111L;
    /**
     * 类目名
     */
    @JsonProperty("name")//序列化返回给前端时，json里面的该字段名为name
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVOList;
}