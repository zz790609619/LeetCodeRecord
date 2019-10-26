package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserInfo {
    @Id
    @GeneratedValue
    private int id;
    private String userinfo;
    public UserInfo(){

    }
    public UserInfo(String userinfo){
        this.userinfo=userinfo;
    }

}
