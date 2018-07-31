package com.hexin.sell.dao;

import com.hexin.sell.pojo.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author hexin
 * @createDate 2018年07月30日 15:28:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("hexin");
        orderMaster.setBuyerPhone("12345678912");
        orderMaster.setBuyerAddress("长沙");
        orderMaster.setBuyerOpenid("100000");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        OrderMaster result =  orderMasterDao.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public  void  findByBuyerOpenidTest(){
        Pageable pageable = new PageRequest(0,2);
        Page<OrderMaster> page = orderMasterDao.findByBuyerOpenid("100000",pageable);
        for (OrderMaster orderMaster:page.getContent()) {

            log.info(orderMaster.getBuyerName());

        }
    }

}