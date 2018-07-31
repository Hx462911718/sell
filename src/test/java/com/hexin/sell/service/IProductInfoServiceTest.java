package com.hexin.sell.service;

import com.hexin.sell.pojo.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hexin
 * @createDate 2018年07月27日 15:41:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IProductInfoServiceTest {
    @Autowired
    private IProductInfoService productInfoService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne("2446c028df714276959fb26e78568bda");
        if (productInfo != null){
            log.info("-------------------------------------------查询成功----------------------------------");
        }
    }

    @Test
    public void findAll() {
        Pageable pageable = new PageRequest(0,2);
        Page<ProductInfo> productInfos = productInfoService.findAll(pageable);
        log.info(String.valueOf(productInfos.getContent()));
    }

    @Test
    public void findByUpProduct() {
    }

    @Test
    public void save() {
    }
}