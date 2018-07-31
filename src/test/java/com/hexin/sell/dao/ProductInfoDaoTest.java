package com.hexin.sell.dao;

import com.hexin.sell.pojo.ProductInfo;
import com.hexin.sell.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hexin
 * @createDate 2018年07月27日 11:48:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoDaoTest {

    @Autowired
    private ProductInfoDao productInfoDao;


    @Test
    public  void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(UUIDUtil.getUUID());
        productInfo.setProductName("黄金大闸蟹");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(10);
        productInfo.setCategoryType(4);
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("xxxxxxxxxxxxxxx");

        productInfoDao.save(productInfo);
    }

    @Test
    public void findByProductStatus() {

       List<ProductInfo> result = productInfoDao.findByProductStatus(0);

       for (ProductInfo info : result){
           log.info(info.toString());
       }

    }
}