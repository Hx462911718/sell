package com.hexin.sell.dao;

import com.hexin.sell.pojo.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hexin
 * @createDate 2018年07月30日 14:58:00
 */
public interface OrderDetailDao extends JpaRepository<OrderDetail,String> {


    /**
     * 根据订单id查询订单详情
     * @param oderId
     * @return
     */
    List<OrderDetail> findByOrderId(String oderId);
}
