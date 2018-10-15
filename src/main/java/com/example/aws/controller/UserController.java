package com.example.aws.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.aws.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
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

    @RequestMapping(value = "qqMusic", method = RequestMethod.GET)
    public ResponseEntity qqMusic(){
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String, String>> list = new LinkedList<>();
        for (int i = 0; i < 1364; i ++){
            System.out.println(i);
            String url = "https://c.y.qq.com/base/fcgi-bin/fcg_global_comment_h5.fcg?g_tk=5381&jsonpCallback=jsoncallback18242116618697346&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=GB2312&notice=0&platform=yqq&needNewCode=0&cid=205360772&reqtype=2&biztype=1&topid=209340068&cmd=8&needmusiccrit=0&pagenum=";
            url = url + i;
            url = url + "&pagesize=25&lasthotcommentid=&callback=jsoncallback18242116618697346&domain=qq.com&ct=24&cv=101010";
            String response = restTemplate.getForObject(url, String.class);
            int a = response.indexOf("(");
            response = response.substring(a+1, response.length());
            int b = response.lastIndexOf(")");
            response = response.substring(0, b);
            JSONObject json = JSONObject.parseObject(response);
            String song_name = json.get("topic_name").toString();
            JSONObject json1 = JSONObject.parseObject(json.get("comment").toString());
            JSONArray jsonArray = JSONArray.parseArray(json1.get("commentlist").toString());
            for(Object obj : jsonArray){
                JSONObject json2 = (JSONObject)obj;
                Map<String, String> map = new HashMap<>();
                map.put("comment_id", json2.get("commentid").toString());
                map.put("comment_user_id", json2.get("uin").toString());
                map.put("comment_user_name", json2.get("nick").toString());
                if(json2.containsKey("rootcommentcontent")){
                    map.put("comment_content", json2.get("rootcommentcontent").toString());
                }else {
                    map.put("comment_content", "");
                }
                map.put("comment_time", json2.get("time").toString());
                map.put("comment_user_icon", json2.get("avatarurl").toString());
                map.put("song_name", song_name);
                list.add(map);
                if(list.size()%1000 == 0){
                    userService.insertComment(list);
                    list.clear();
                }
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
