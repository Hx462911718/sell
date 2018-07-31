package com.hexin.sell.controller;

import com.hexin.sell.Common.ResultVO;
import com.hexin.sell.Common.ResultVOUtil;
import com.hexin.sell.Common.ServiceResponse;
import com.hexin.sell.pojo.ProductCategory;
import com.hexin.sell.pojo.ProductInfo;
import com.hexin.sell.service.IProductCategoryService;
import com.hexin.sell.service.IProductInfoService;
import com.hexin.sell.vo.ProductInfoVo;
import com.hexin.sell.vo.ProductVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hexin
 * @createDate 2018年07月27日 15:56:00
 */
@Controller
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private IProductInfoService productInfoService;
    @Autowired
    private IProductCategoryService productCategoryService;

    @GetMapping("/list")
    @ResponseBody
    public ResultVO list(){
        //1.查询所有上架商品
        List<ProductInfo> productInfoList =productInfoService.findByUpProduct();
        //2.查询分类
//        List<Integer> categoryTypeList = new ArrayList<>();
//        //传统方法
//        for (ProductInfo productInfo : productInfoList){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //java8 lambda
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e->e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼装
        List<ProductVo> productVoList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList){
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType() == productVo.getCategoryType()){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                   productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoVOList(productInfoVoList);
            productVoList.add(productVo);
        }

        return ResultVOUtil.success(productVoList);
    }
}
