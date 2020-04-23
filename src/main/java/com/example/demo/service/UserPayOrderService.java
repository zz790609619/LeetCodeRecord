package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.data.mapper.user.UserPayOrderMapper;
import com.example.demo.entity.SubmitUserPayOrderRequest;
import com.example.demo.entity.UserPayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author ww
 * @Date 2020-04-22
 */
@Service
public class UserPayOrderService {

    @Autowired
    private UserPayOrderMapper userPayOrderDao;



    /**
     * 根据订单号获取商户id
     *
     * @param userOrderId
     * @return
     */
    public String userOrderMerchantId(String userOrderId) {
        UserPayOrder upo = userPayOrderDao.selectByEntity(new UserPayOrder()
            .setId(userOrderId));
        return JSON.toJSONString(upo);
    }


    /**
     * 提交订单
     *
     * @param userPayOrderRequest
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public String submit(SubmitUserPayOrderRequest userPayOrderRequest) {

                Date createTime = new Date();
                userPayOrderDao.insertSelective(new UserPayOrder()
                        .setId(userPayOrderRequest.getId())
                        .setMerchantId(userPayOrderRequest.getMerchantId())
                        .setUserPayId(userPayOrderRequest.getUserPayId())
                        .setAmountOfConsumption(userPayOrderRequest.getAmountOfConsumption())
                        .setPayAbleAmount(userPayOrderRequest.getPayAblAmount())
                        .setUserId(userPayOrderRequest.getUserId())
                        .setDiscountAmount(userPayOrderRequest.getDiscountAmount())
                        .setFreeAmount(userPayOrderRequest.getFreeAmount())
                        .setStatus(userPayOrderRequest.getStatus())
                        .setChannelId(userPayOrderRequest.getChannelId())
                        .setEnjoyKingAmount(userPayOrderRequest.getEnjoyKingAmount())
                        .setPayType(userPayOrderRequest.getPayType())
                        .setScanType(userPayOrderRequest.getScanType())
                        .setCreateTime(createTime));
                return "xx";
    }
}
