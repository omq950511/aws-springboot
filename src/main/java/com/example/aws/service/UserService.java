package com.example.aws.service;

import com.example.aws.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public List<Map<String, String>> queryUserList(){
        return userDao.queryUserList();
    }


    public Map<String, String> queryUserByName(String username){
        return userDao.queryUserByName(username);
    }
}
