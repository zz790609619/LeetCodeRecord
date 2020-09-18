package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.mq.AliMqComponent;
import com.example.demo.service.MakeUpBonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionMqController {
    @Autowired
    private MakeUpBonusService makeUpBonusService;

    @Autowired
    private AliMqComponent aliMqComponent;
    /**
     * 测试事务mq发送端
     */
    @GetMapping("/testTransactionMq")
    public String queryUser(){
        String messageId="";
        try {
            aliMqComponent.pushTransactionByTcp("topic","group","key", JSON.toJSONBytes("Json"));
        }catch (Exception e){
            System.out.println("sad");
        }

        return messageId;
    }


}