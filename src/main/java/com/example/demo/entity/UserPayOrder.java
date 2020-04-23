 package com.example.demo.entity;

 import java.math.BigDecimal;
 import java.util.Date;

 /**
  * @Author ww
  * @Date 2020-04-22
  */
 public class UserPayOrder {

     /** id */
     private String id;

     /** 商家Id */
     private Long merchantId;

     /** 支付用户id */
     private Long userPayId;

     /** 消费金额 */
     private BigDecimal amountOfConsumption;

     /** 实际支付的金额 */
     private BigDecimal payAbleAmount;

     /** 商家实际可收金额 */
     private BigDecimal merchantReceiveAmount;

     /** 支付类型(0微信、1支付宝、汇潮) */
     private Integer payType;

     /** 支付成功时间 */
     private Date payTime;

     /** 主账户id */
     private Long userId;

     /** 抵扣卷金额 */
     private BigDecimal discountAmount;

     /** 免单卷金额 */
     private BigDecimal freeAmount;

     /** 1:成功;2:用户取消;3:失败;4:待支付 */
     private Integer status;

     /** 渠道 1:微信、2支付宝、3:菜信 */
     private Integer channelId;

     /** 享利金金额 */
     private BigDecimal enjoyKingAmount;

     /** 创建时间 */
     private Date createTime;

     /** 修改时间 */
     private Date updateTime;

     /** 发放享利金金额 */
     private BigDecimal enjoyIssueKingAmount;

     /** 用户实收享利金金额 */
     private BigDecimal enjoyReceiveKingAmount;

     /** 扫码方式(0：客户被扫、1：客户主扫) */
     private Integer scanType;


  /**
      * 获取 id 的值
      * @return String
      */
     public String getId() {
         return id;
     }

     /**
      * 设置id 的值
      * @param String id
      */
     public UserPayOrder setId(String id) {
         this.id = id;
         return this;
     }
  /**
      * 获取 商家Id 的值
      * @return Long
      */
     public Long getMerchantId() {
         return merchantId;
     }

     /**
      * 设置商家Id 的值
      * @param Long merchantId
      */
     public UserPayOrder setMerchantId(Long merchantId) {
         this.merchantId = merchantId;
         return this;
     }
  /**
      * 获取 支付用户id 的值
      * @return Long
      */
     public Long getUserPayId() {
         return userPayId;
     }

     /**
      * 设置支付用户id 的值
      * @param Long userPayId
      */
     public UserPayOrder setUserPayId(Long userPayId) {
         this.userPayId = userPayId;
         return this;
     }
  /**
      * 获取 消费金额 的值
      * @return BigDecimal
      */
     public BigDecimal getAmountOfConsumption() {
         return amountOfConsumption;
     }

     /**
      * 设置消费金额 的值
      * @param BigDecimal amountOfConsumption
      */
     public UserPayOrder setAmountOfConsumption(BigDecimal amountOfConsumption) {
         this.amountOfConsumption = amountOfConsumption;
         return this;
     }
  /**
      * 获取 实际支付的金额 的值
      * @return BigDecimal
      */
     public BigDecimal getPayAbleAmount() {
         return payAbleAmount;
     }

     /**
      * 设置实际支付的金额 的值
      * @param BigDecimal payAbleAmount
      */
     public UserPayOrder setPayAbleAmount(BigDecimal payAbleAmount) {
         this.payAbleAmount = payAbleAmount;
         return this;
     }
  /**
      * 获取 商家实际可收金额 的值
      * @return BigDecimal
      */
     public BigDecimal getMerchantReceiveAmount() {
         return merchantReceiveAmount;
     }

     /**
      * 设置商家实际可收金额 的值
      * @param BigDecimal merchantReceiveAmount
      */
     public UserPayOrder setMerchantReceiveAmount(BigDecimal merchantReceiveAmount) {
         this.merchantReceiveAmount = merchantReceiveAmount;
         return this;
     }
  /**
      * 获取 支付类型(0微信、1支付宝、汇潮) 的值
      * @return Integer
      */
     public Integer getPayType() {
         return payType;
     }

     /**
      * 设置支付类型(0微信、1支付宝、汇潮) 的值
      * @param Integer payType
      */
     public UserPayOrder setPayType(Integer payType) {
         this.payType = payType;
         return this;
     }
  /**
      * 获取 支付成功时间 的值
      * @return Date
      */
     public Date getPayTime() {
         return payTime;
     }

     /**
      * 设置支付成功时间 的值
      * @param Date payTime
      */
     public UserPayOrder setPayTime(Date payTime) {
         this.payTime = payTime;
         return this;
     }
  /**
      * 获取 主账户id 的值
      * @return Long
      */
     public Long getUserId() {
         return userId;
     }

     /**
      * 设置主账户id 的值
      * @param Long userId
      */
     public UserPayOrder setUserId(Long userId) {
         this.userId = userId;
         return this;
     }
  /**
      * 获取 抵扣卷金额 的值
      * @return BigDecimal
      */
     public BigDecimal getDiscountAmount() {
         return discountAmount;
     }

     /**
      * 设置抵扣卷金额 的值
      * @param BigDecimal discountAmount
      */
     public UserPayOrder setDiscountAmount(BigDecimal discountAmount) {
         this.discountAmount = discountAmount;
         return this;
     }
  /**
      * 获取 免单卷金额 的值
      * @return BigDecimal
      */
     public BigDecimal getFreeAmount() {
         return freeAmount;
     }

     /**
      * 设置免单卷金额 的值
      * @param BigDecimal freeAmount
      */
     public UserPayOrder setFreeAmount(BigDecimal freeAmount) {
         this.freeAmount = freeAmount;
         return this;
     }
  /**
      * 获取 1:成功;2:用户取消;3:失败;4:待支付 的值
      * @return Integer
      */
     public Integer getStatus() {
         return status;
     }

     /**
      * 设置1:成功;2:用户取消;3:失败;4:待支付 的值
      * @param Integer status
      */
     public UserPayOrder setStatus(Integer status) {
         this.status = status;
         return this;
     }
  /**
      * 获取 渠道 1:微信、2支付宝、3:菜信 的值
      * @return Integer
      */
     public Integer getChannelId() {
         return channelId;
     }

     /**
      * 设置渠道 1:微信、2支付宝、3:菜信 的值
      * @param Integer channelId
      */
     public UserPayOrder setChannelId(Integer channelId) {
         this.channelId = channelId;
         return this;
     }
  /**
      * 获取 享利金金额 的值
      * @return BigDecimal
      */
     public BigDecimal getEnjoyKingAmount() {
         return enjoyKingAmount;
     }

     /**
      * 设置享利金金额 的值
      * @param BigDecimal enjoyKingAmount
      */
     public UserPayOrder setEnjoyKingAmount(BigDecimal enjoyKingAmount) {
         this.enjoyKingAmount = enjoyKingAmount;
         return this;
     }
  /**
      * 获取 创建时间 的值
      * @return Date
      */
     public Date getCreateTime() {
         return createTime;
     }

     /**
      * 设置创建时间 的值
      * @param Date createTime
      */
     public UserPayOrder setCreateTime(Date createTime) {
         this.createTime = createTime;
         return this;
     }
  /**
      * 获取 修改时间 的值
      * @return Date
      */
     public Date getUpdateTime() {
         return updateTime;
     }

     /**
      * 设置修改时间 的值
      * @param Date updateTime
      */
     public UserPayOrder setUpdateTime(Date updateTime) {
         this.updateTime = updateTime;
         return this;
     }
  /**
      * 获取 发放享利金金额 的值
      * @return BigDecimal
      */
     public BigDecimal getEnjoyIssueKingAmount() {
         return enjoyIssueKingAmount;
     }

     /**
      * 设置发放享利金金额 的值
      * @param BigDecimal enjoyIssueKingAmount
      */
     public UserPayOrder setEnjoyIssueKingAmount(BigDecimal enjoyIssueKingAmount) {
         this.enjoyIssueKingAmount = enjoyIssueKingAmount;
         return this;
     }
  /**
      * 获取 用户实收享利金金额 的值
      * @return BigDecimal
      */
     public BigDecimal getEnjoyReceiveKingAmount() {
         return enjoyReceiveKingAmount;
     }

     /**
      * 设置用户实收享利金金额 的值
      * @param BigDecimal enjoyReceiveKingAmount
      */
     public UserPayOrder setEnjoyReceiveKingAmount(BigDecimal enjoyReceiveKingAmount) {
         this.enjoyReceiveKingAmount = enjoyReceiveKingAmount;
         return this;
     }

     public Integer getScanType() {
         return scanType;
     }

     public UserPayOrder setScanType(Integer scanType) {
         this.scanType = scanType;
         return this;
     }


 }