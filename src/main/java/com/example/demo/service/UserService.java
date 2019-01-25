package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {

    /**
     * 根据用户名查找电话号码
     * @param username
     * @return
     */
    String getPhoneByUsername(String username);

    /**
     * 根据ID查找用户
     * @param id
     * @return
     */
    User getUserById(int id);
}
