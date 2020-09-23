package com.example.demo.entity;

public class MakeUpBonus {
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 支付用户Id
     */
    private Long userId;

    /**
     * 商户Id
     */
    private Long merchantId;

    /**
     * 0是微信，1是支付宝
     */
    private Integer payType;

    /**
     * 是否补偿
     */
    private Boolean makeUpFlag;


    public String getOrderId() {
        return orderId;
    }

    public MakeUpBonus setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Boolean getMakeUpFlag() {
        return makeUpFlag;
    }

    public void setMakeUpFlag(Boolean makeUpFlag) {
        this.makeUpFlag = makeUpFlag;
    }
}
