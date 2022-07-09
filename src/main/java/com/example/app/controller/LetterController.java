package com.example.app.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.app.service.DataService;
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

    @Autowired
    private DataService dataService;


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

    @PostMapping("/deleteIndex")
    public Result<String> deleteIndex(@RequestParam int indexNum){
        try {
            return indexService.deleteIndex(indexNum);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/queryByIndexNum")
    public Result<JSONObject> queryByIndexNum(@RequestParam int indexNum){
        try {
            return indexService.queryByIndexNum(indexNum);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/queryAllIndex")
    public Result<JSONArray> queryAllIndex(){
        try {
            return indexService.queryAllIndex();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/queryAllIndexCode")
    public Result<String> queryAllIndexCode(){
        try {
            return indexService.queryAllIndexCode();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/initDataFile")
    public Result<String> initDataFile(){
        try {
            return dataService.initDataFile();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/getData")
    public Result<String> getData(@RequestBody JSONArray jsonArray){
        try {
            return dataService.getData(jsonArray);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/getDataJSONObject")
    public Result<JSONObject> getDataJSONObject(){
        try {
            return dataService.getDataJSONObject();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
