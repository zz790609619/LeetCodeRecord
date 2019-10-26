package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController{
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registerUser",method = RequestMethod.POST)
    @ResponseBody
    public int insert()throws InterruptedException {
        Thread.sleep(10000L);
         return  0;
    }
//    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
//    @ResponseBody
//    public int updateUserInfo(@ModelAttribute User user){
//        return userService.updateUserInfo(user);
//
//    }
}