package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.cachetest.ConfigerCache;
import com.example.demo.entity.MakeUpBonus;
import com.example.demo.entity.SubmitUserPayOrderRequest;
import com.example.demo.entity.UserPayOrder;
import com.example.demo.mq.AliMqComponent;
import com.example.demo.service.MakeUpBonusService;
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
    @Autowired
    private MakeUpBonusService makeUpBonusService;

    @Autowired
    private AliMqComponent aliMqComponent;
    /**
     * 根据订单号获取商户id
     * @param userOrderId 订单id
     * @return
     */
    @PostMapping("/userOrderMerchantId")
    public String userOrderMerchantId(@RequestParam("userOrderId") String userOrderId){
        UserPayOrder vo=userPayOrderService.userOrderMerchantId(userOrderId);
        String messageId = aliMqComponent.sendCommonMessage("ww_test_topic","ww_test_tag","ssss",JSON.toJSONBytes(vo));
        return JSON.toJSONString(vo);
    }

    @PostMapping("/submit")
    public String submit(@RequestBody SubmitUserPayOrderRequest userPayOrderRequest){
        return userPayOrderService.submit(userPayOrderRequest);
    }

    @PostMapping("/testCache")
    public void testMybatisCache(){
        ConfigerCache configerCache=new ConfigerCache();
        configerCache.setCacheQ("11","11");
        System.out.println("1111");
    }
    @PostMapping("/insertMake")
    public void testMybatisCache(@RequestBody MakeUpBonus makeUpBonus){
        makeUpBonusService.insertMakeUpBonus(makeUpBonus);
    }

}