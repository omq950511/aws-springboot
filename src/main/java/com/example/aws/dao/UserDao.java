package com.example.aws.dao;

import java.util.List;
import java.util.Map;

public interface UserDao {

    List<Map<String, String>> queryUserList();

    Map<String, String> queryUserByName(String username);

    void insertComment(List<Map<String, String>> list);
}
