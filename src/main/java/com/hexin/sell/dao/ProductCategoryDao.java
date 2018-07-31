package com.hexin.sell.dao;

import com.hexin.sell.pojo.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hexin
 * @createDate 2018年07月27日 10:16:00
 */
public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {


    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);

}
