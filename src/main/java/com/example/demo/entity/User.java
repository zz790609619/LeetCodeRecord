package com.example.demo.entity;

public class User {
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户token
     */
    private String token;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
