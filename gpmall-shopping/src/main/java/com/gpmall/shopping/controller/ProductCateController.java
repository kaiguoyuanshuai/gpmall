package com.gpmall.shopping.controller;

import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.shopping.IProductCateService;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.dto.AllProductCateRequest;
import com.gpmall.shopping.dto.AllProductCateResponse;
import com.gpmall.user.annotation.Anoymous;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by oahnus on 2019/8/8
 * 22:34.
 */
@Slf4j
@RestController
@RequestMapping("/shopping")
public class ProductCateController {
    @Reference(timeout = 3000)
    IProductCateService productCateService;

    @Anoymous
    @GetMapping("/categories")
    public ResponseData allProductCateList(@RequestParam(
            value = "sort",
            required = false,
            defaultValue = "asc")String sort){
        AllProductCateRequest request = new AllProductCateRequest();
        request.setSort(sort);
        AllProductCateResponse response= productCateService.getAllProductCate(request);

        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getProductCateDtoList());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
}
