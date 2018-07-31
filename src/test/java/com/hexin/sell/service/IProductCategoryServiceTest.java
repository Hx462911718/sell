package com.hexin.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hexin
 * @createDate 2018年07月27日 11:08:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IProductCategoryServiceTest {
    @Autowired
    private IProductCategoryService productCategoryService;

    @Test
    public void findOne() {
        log.info(productCategoryService.findOne(1).toString());
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findByCategoryTypeIn() {
    }

    @Test
    public void save() {
    }
}