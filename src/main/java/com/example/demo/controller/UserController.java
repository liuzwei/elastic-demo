package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/phone")
    @ResponseBody
    public String getPhone(String username){
        if (StringUtils.isEmpty(username)){
            return "参数不能为空";
        }
        System.out.println("传进来的参数："+username);

        return userService.getPhoneByUsername(username);
    }


    @RequestMapping("/getUser")
    @ResponseBody
    public String getUserById(int id){
        User user = userService.getUserById(id);

        return JSONObject.toJSONString(user);
    }
}
