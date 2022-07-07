package com.example.app.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.app.service.FileService;
import com.example.app.common.Result;
import com.example.app.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/letter")
public class LetterController {

    @Autowired
    private FileService jsonUtils;

    @Autowired
    private IndexService indexService;


    //写信
    @PostMapping("/addLetter")
    public Result<JSONArray> createSingleLetter(){
        try {
            return Result.success(jsonUtils.readFile());

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/addIndex")
    public Result<String> addIndex(@RequestBody JSONObject index){
        try {
            return indexService.addIndex(index);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/modifyIndex")
    public Result<String> modifyIndex(@RequestBody JSONObject index){
        try {
            return indexService.modifyIndex(index);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



}
