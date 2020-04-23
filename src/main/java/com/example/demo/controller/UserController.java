package com.example.demo.controller;

import com.example.demo.entity.SubmitUserPayOrderRequest;
import com.example.demo.service.UserPayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController{
    @Autowired
    private UserPayOrderService userPayOrderService;

    /**
     * 根据订单号获取商户id
     * @param userOrderId 订单id
     * @return
     */
    @PostMapping("/userOrderMerchantId")
    public String userOrderMerchantId(@RequestParam("userOrderId") String userOrderId){
        return userPayOrderService.userOrderMerchantId(userOrderId);
    }
    @PostMapping("/submit")

    public String submit(@RequestBody SubmitUserPayOrderRequest userPayOrderRequest){
        return userPayOrderService.submit(userPayOrderRequest);

    }
}