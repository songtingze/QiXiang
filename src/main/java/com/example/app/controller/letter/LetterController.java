package com.example.app.controller.letter;


import com.example.app.common.Result;
import com.example.app.entity.Letter;
import com.example.app.entity.User;
import com.example.app.service.LetterService;
import com.github.pagehelper.PageInfo;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/letter")

public class LetterController {
    @Autowired
    private LetterService letterService;


    @PostMapping("/add")
    public Result<Letter> createSingleLetter(@RequestBody Letter newLetter){
        try {

            //生成唯一标识lid
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            newLetter.setLid("L_" + uuid.substring(0,10));

            //设置创建时间
            Timestamp t = new Timestamp(System.currentTimeMillis());
            String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t);
            newLetter.setCreateTime(createTime);
            letterService.addLetter(newLetter);
            return Result.success(newLetter);

        }catch (Exception e){

            e.printStackTrace();
            return null;
        }
    }


    @PostMapping("/selectLetter")
    public Result<PageInfo<Letter>> queryByReceiveUid(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam String receiveUid){
        PageInfo pageInfo = letterService.queryReceiveUid(pageNum,pageSize,receiveUid);

        return Result.success(pageInfo);
    }




}
