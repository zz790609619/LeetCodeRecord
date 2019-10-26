package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserDao userDao;
    @Override
    public int insertUser(User user) {
        return 0;
       // return  userDao.insertUser(user.getUsername(),user.getPassword());
    }
}
