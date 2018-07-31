package com.hexin.sell.service;

import com.hexin.sell.dto.CartDto;
import com.hexin.sell.pojo.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品信息service
 * @author hexin
 * @createDate 2018年07月27日 15:00:00
 */
public interface IProductInfoService {

    ProductInfo findOne(String productId);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    List<ProductInfo> findByUpProduct();

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void  increaseStock(List<CartDto> cartDtoList);

    void  decreaseStock(List<CartDto> cartDtoList);



}
