package com.example.app.repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.app.common.Result;
import com.example.app.service.DataService;
import com.example.app.service.FileService;
import com.example.app.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IndexRepository {
    @Autowired
    private FileService fileService;

    @Autowired
    private DataService dataService;

    @Autowired
    private IndexService indexService;

    //添加指标
    public Result<String> addIndex(JSONObject jsonObject) throws IOException {
        String msg = indexService.isIndexRight(jsonObject);
        if(msg.equalsIgnoreCase("success")){
            JSONArray jsonArray = fileService.readJSONArray();
            jsonObject.put("indexNum",jsonArray.size());
            JSONObject data = new JSONObject();
            data.put("dataNum",jsonArray.size());
            data.put("indexCode",jsonObject.getString("indexCode"));
            data.put("data","");
            data.put("dataStatus","UnKnown");
            jsonArray.add(jsonObject);
            fileService.writeJSONArray(jsonArray);
            dataService.addData(data);
            return Result.success("气象指标添加成功！");
        }
        else{
            return Result.error("101",msg);
        }

    }

    //修改指标
    public Result<String> modifyIndex(JSONObject index) throws IOException{
        String msg = indexService.isIndexRight(index);
        if(msg.equalsIgnoreCase("success")){
            JSONArray jsonArray = fileService.readJSONArray();
            int indexNum = index.getInteger("indexNum");
            JSONObject jsonObject = jsonArray.getJSONObject(indexNum);
            jsonArray.remove(jsonObject);
            jsonObject.put("indexName",index.getString("indexName"));
            jsonObject.put("indexCode",index.getString("indexCode"));
            jsonObject.put("indexData",index.getString("indexData"));
            jsonObject.put("indexJudge",index.getString("indexJudge"));
            jsonObject.put("indexStatus",index.getString("indexStatus"));
            jsonArray.add(indexNum,jsonObject);
            fileService.writeJSONArray(jsonArray);
            JSONObject data = new JSONObject();
            data.put("dataNum",indexNum);
            data.put("indexCode",index.getString("indexCode"));
            dataService.modifyData(data);
            return Result.success("气象指标修改成功！");
        }
        else{
            return Result.error("101",msg);
        }

    }

    //删除指标
    public Result<String> deleteIndex(int indexNum) throws IOException {
        JSONArray jsonArray = fileService.readJSONArray();
        JSONObject jsonObject = jsonArray.getJSONObject(indexNum);
        jsonArray.remove(jsonObject);
        for(int i = indexNum;i < jsonArray.size(); i ++){
            JSONObject index = jsonArray.getJSONObject(i);
            jsonArray.remove(index);
            index.put("indexNum",i);
            jsonArray.add(i,index);
        }
        fileService.writeJSONArray(jsonArray);
        dataService.deleteData(indexNum);
        return Result.success("气象指标删除成功！");
    }
}
