package com.hexin.sell.service.impl;

import com.hexin.sell.converter.OrderMaster2OrderDtoConverter;
import com.hexin.sell.dao.OrderDetailDao;
import com.hexin.sell.dao.OrderMasterDao;
import com.hexin.sell.dto.CartDto;
import com.hexin.sell.dto.OrderDto;
import com.hexin.sell.enums.OrderStatus;
import com.hexin.sell.enums.PayStatusEnum;
import com.hexin.sell.enums.ResultEnum;
import com.hexin.sell.exception.SellException;
import com.hexin.sell.pojo.OrderDetail;
import com.hexin.sell.pojo.OrderMaster;
import com.hexin.sell.pojo.ProductInfo;
import com.hexin.sell.service.IOrderService;
import com.hexin.sell.service.IProductInfoService;
import com.hexin.sell.util.BeanCopyUtil;
import com.hexin.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hexin
 * @createDate 2018年07月30日 16:10:00
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IProductInfoService productInfoService;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private OrderMasterDao orderMasterDao;

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1.查询商品(数量，价格)
        for(OrderDetail orderDetail : orderDto.getOrderDetailList()){
            ProductInfo productInfo =  productInfoService.findOne(orderDetail.getProductId());

            if(productInfo == null) {
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价
            orderAmount = productInfo.getProductPrice().multiply
                    (new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            //插入订单项
            orderDetailDao.save(orderDetail);
        }
        OrderMaster orderMaster= new  OrderMaster();
        orderDto.setOrderId(orderId);
        orderDto.setOrderAmount(orderAmount);
        BeanCopyUtil.copyNotNullProperties(orderDto,orderMaster);
        //3.写入数据库
        orderMasterDao.save(orderMaster);
        //4.扣除库存
        List<CartDto> cartDtoList = new ArrayList<>();
        cartDtoList = orderDto.getOrderDetailList()
                .stream().map(e -> new CartDto(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDtoList);
        return orderDto;
    }

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    @Override
    public OrderDto findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw  new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDto orderDto = new OrderDto();
        BeanCopyUtil.copyNotNullProperties(orderMaster,orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> page = orderMasterDao.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDto> orderDtoList = OrderMaster2OrderDtoConverter.convert(page.getContent());
        Page<OrderDto> orderDtoPage = new PageImpl<>(orderDtoList,pageable,page.getTotalElements());
        return orderDtoPage;
    }

    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatus.NEW.getCode())){
            log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw  new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderStatus.CANCEL.getCode());
        BeanCopyUtil.copyNotNullProperties(orderDto,orderMaster);
        OrderMaster orderMasterResult = orderMasterDao.save(orderMaster);
        if(orderMasterResult == null){
            log.error("【取消订单】更新订单状态失败,orderMast={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返还库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【取消订单】订单无商品");
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDtoList);
        //退款（已支付）
        if(orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            //TODO 退款操作
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatus.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw  new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderStatus.FINSHED.getCode());
        BeanCopyUtil.copyNotNullProperties(orderDto,orderMaster);
        OrderMaster orderMasterResult = orderMasterDao.save(orderMaster);
        if(orderMasterResult == null){
            log.error("【完结订单】更新订单状态失败,orderMast={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatus.NEW.getCode())){
            log.error("【支付成功】订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw  new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        //判断支付状态
        if(!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付成功】订单支付状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getPayStatus());
            throw  new SellException(ResultEnum.ORDER_PAY_STATUS_ERROP);
        }
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanCopyUtil.copyNotNullProperties(orderDto,orderMaster);
        OrderMaster orderMasterResult = orderMasterDao.save(orderMaster);
        if (orderMaster == null){
            log.error("【支付成功】更新订单状态失败,orderMast={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }
}
