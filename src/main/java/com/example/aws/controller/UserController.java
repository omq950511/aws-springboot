package com.example.aws.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.aws.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Resource
    private UserService userService;


    @RequestMapping(value = "queryUserList")
    public ResponseEntity queryUserList(){
        List<Map<String, String>> userList = userService.queryUserList();
        JSONObject json = new JSONObject();
        json.put("userList", userList);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @RequestMapping(value = "queryUserByName")
    public ResponseEntity queryUserByName(){
        Map<String, String> user = userService.queryUserByName("欧明棋");
        JSONObject json = new JSONObject();
        json.put("user", user);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
