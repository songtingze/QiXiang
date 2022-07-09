package com.example.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.app.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Pattern;

@Service
public class IndexService {

    @Autowired
    private FileService fileService;

    public boolean isNumeric(String indexData){
        Pattern pattern = Pattern.compile("[0-9]*");
        if(indexData.startsWith("-")){
            indexData = indexData.replace("-","");
        }
        if(indexData.indexOf(".")>0){//判断是否有小数点
            if(indexData.indexOf(".")==indexData.lastIndexOf(".") && indexData.split("\\.").length==2){ //判断是否只有一个小数点
                return pattern.matcher(indexData.replace(".","")).matches();
            }else {
                return false;
            }
        } else {
            return pattern.matcher(indexData).matches();
        }
    }

    public String isIndexRight(JSONObject jsonObject) throws IOException {
        String indexName = jsonObject.getString("indexName");
        String indexCode = jsonObject.getString("indexCode");
        String indexData = jsonObject.getString("indexData");
        String msg = "";
        JSONArray jsonArray = fileService.readFile();
        for(int i = 0; i< jsonArray.size();i ++){
            JSONObject index = jsonArray.getJSONObject(i);
            if(indexName.equalsIgnoreCase(index.getString("indexName"))){
                msg = "该气象指标名称已存在!";
                return msg;
            }
            if(indexCode.equalsIgnoreCase(index.getString("indexCode"))){
                msg = "该气象指标代码已存在!";
                return msg;
            }
        }
        if(!isNumeric(indexData)){
            msg = "该气象指标代码已存在!";
            return msg;
        }
        msg = "success";
        return msg;
    }

    public Result<String> addIndex(JSONObject jsonObject) throws IOException {
        String msg = isIndexRight(jsonObject);
        if(msg.equalsIgnoreCase("success")){
            JSONArray jsonArray = fileService.readFile();

            jsonObject.put("indexNum",jsonArray.size());
            jsonArray.add(jsonObject);
            fileService.writeFile(jsonArray);
            return Result.success("气象指标添加成功！");
        }
        else{
            return Result.error("101",msg);
        }

    }

    public Result<String> modifyIndex(JSONObject index) throws IOException{
        String msg = isIndexRight(index);
        if(msg.equalsIgnoreCase("success")){
            JSONArray jsonArray = fileService.readFile();
            int indexNum = index.getInteger("indexNum");
            JSONObject jsonObject = jsonArray.getJSONObject(indexNum);
            jsonArray.remove(jsonObject);
            jsonObject.put("indexName",index.getString("indexName"));
            jsonObject.put("indexCode",index.getString("indexCode"));
            jsonObject.put("indexData",index.getString("indexData"));
            jsonObject.put("indexJudge",index.getString("indexJudge"));
            jsonObject.put("indexStatus",index.getString("indexStatus"));
            jsonArray.add(indexNum,jsonObject);
            fileService.writeFile(jsonArray);
            return Result.success("气象指标修改成功！");
        }
        else{
            return Result.error("101",msg);
        }

    }


}
