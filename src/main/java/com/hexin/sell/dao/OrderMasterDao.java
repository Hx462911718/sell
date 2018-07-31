package com.hexin.sell.dao;

import com.hexin.sell.pojo.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hexin
 * @createDate 2018年07月30日 14:58:00
 */
public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {

    /**
     * 根据买家id,分页查询
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
