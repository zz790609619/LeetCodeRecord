package com.example.demo.entity;

import java.math.BigDecimal;

public class SubmitUserPayOrderRequest {
    private String id;
    private Long merchantId;
    private Long userPayId;
    private BigDecimal amountOfConsumption;
    private BigDecimal payAblAmount;
    private Long userId;
    private BigDecimal discountAmount;
    private BigDecimal freeAmount;
    private String discountId;
    private String freeId;
    private Integer status;
    private Integer channelId;
    private BigDecimal enjoyKingAmount;
    private Integer payType;
    private Integer scanType;

    public SubmitUserPayOrderRequest() {
    }

    public String getId() {
        return this.id;
    }

    public Long getMerchantId() {
        return this.merchantId;
    }

    public Long getUserPayId() {
        return this.userPayId;
    }

    public BigDecimal getAmountOfConsumption() {
        return this.amountOfConsumption;
    }

    public BigDecimal getPayAblAmount() {
        return this.payAblAmount;
    }

    public Long getUserId() {
        return this.userId;
    }

    public BigDecimal getDiscountAmount() {
        return this.discountAmount;
    }

    public BigDecimal getFreeAmount() {
        return this.freeAmount;
    }

    public String getDiscountId() {
        return this.discountId;
    }

    public String getFreeId() {
        return this.freeId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public BigDecimal getEnjoyKingAmount() {
        return this.enjoyKingAmount;
    }

    public Integer getPayType() {
        return this.payType;
    }

    public Integer getScanType() {
        return this.scanType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public void setUserPayId(Long userPayId) {
        this.userPayId = userPayId;
    }

    public void setAmountOfConsumption(BigDecimal amountOfConsumption) {
        this.amountOfConsumption = amountOfConsumption;
    }

    public void setPayAblAmount(BigDecimal payAblAmount) {
        this.payAblAmount = payAblAmount;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setFreeAmount(BigDecimal freeAmount) {
        this.freeAmount = freeAmount;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public void setFreeId(String freeId) {
        this.freeId = freeId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public void setEnjoyKingAmount(BigDecimal enjoyKingAmount) {
        this.enjoyKingAmount = enjoyKingAmount;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public void setScanType(Integer scanType) {
        this.scanType = scanType;
    }
}
