package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl implements UserService {


    @Override
    public String getPhoneByUsername(String username) {

        //TODO 调用数据库层查找数据并返回

        return "18812341234";
    }

    @Override
    public User getUserById(int id) {

        User user = new User();

        user.setId(id);
        user.setUsernmae("张三");
        user.setTelphone("18812341234");

        return user;
    }
}
