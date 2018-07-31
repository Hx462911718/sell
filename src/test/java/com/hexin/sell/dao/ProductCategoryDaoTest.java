package com.hexin.sell.dao;

import com.hexin.sell.pojo.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * 商品分类测试类
 * @author hexin
 * @createDate 2018年07月27日 10:17:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryDaoTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public  void findOneTest(){
        ProductCategory productCategory = productCategoryDao.findOne(1);
        log.info(productCategory.toString());

    }
    @Test
    public  void saveTest(){
        ProductCategory productCategory = productCategoryDao.findOne(2);
        productCategory.setCategoryName("电脑");
        productCategory.setCategoryType(4);
        productCategoryDao.save(productCategory);
    }

    @Test
    public  void findByCategoryTypeIn(){
        List list = Arrays.asList(2,3,4);
        List<ProductCategory> result = productCategoryDao.findByCategoryTypeIn(list);
        log.info(String.valueOf(result.size()));
    }
}