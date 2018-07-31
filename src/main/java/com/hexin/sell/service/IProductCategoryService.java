package com.hexin.sell.service;

import com.hexin.sell.pojo.ProductCategory;

import java.util.List;

/**
 * 分类service
 * @author hexin
 * @createDate 2018年07月27日 10:53:00
 */
public interface IProductCategoryService {

    ProductCategory findOne(Integer id);

    List<ProductCategory>  findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
