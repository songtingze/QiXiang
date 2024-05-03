package com.example.app.repository;

import com.alibaba.fastjson.JSONObject;
import com.example.app.common.Result;
import com.example.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class TestController {

    @Autowired
    private DataRepository dataRepository;

//    //用户登录
//    @PostMapping("/login")
//    public Result<String> login(@RequestBody JSONObject jsonObject){
//        try{
//            return dataRepository.getData(jsonObject);
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//
//    }

}
