package com.example.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.app.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DataService {
    @Autowired
    private FileService fileService;

    public Result<String> initDataFile() throws IOException {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataTime = new JSONObject();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String times = dateFormat.format(new Date());
        dataTime.put("time",times);
        jsonObject.put("dataTime",dataTime);
        JSONArray dataArray = new JSONArray();
        JSONArray indexArray = fileService.readJSONArray();
        for(int i = 0 ; i < indexArray.size();i ++){
            JSONObject data = new JSONObject();
            JSONObject index = indexArray.getJSONObject(i);
            data.put("dataNum",i);
            data.put("indexCode",index.getString("indexCode"));
            data.put("data","");
            data.put("dataStatus","UnKnown");
            dataArray.add(data);
        }
        jsonObject.put("data",dataArray);
        fileService.writeJSONObject(jsonObject);
        return Result.success("数据文件初始化成功！");
    }

    public void addData(JSONObject data) throws IOException {
        JSONObject jsonObject = fileService.readJSONObject();
        JSONArray dataArray = new JSONArray();
        if(jsonObject.get("data") != null){
            dataArray = jsonObject.getJSONArray("data");
        }
        dataArray.add(data);
        jsonObject.put("data",dataArray);
        fileService.writeJSONObject(jsonObject);
    }

    public void modifyData(JSONObject data) throws IOException {
        JSONObject dataJson = fileService.readJSONObject();
        JSONArray dataArray = dataJson.getJSONArray("data");
        int indexNum = data.getInteger("dataNum");
        JSONObject jsonObject = dataArray.getJSONObject(indexNum);
        dataArray.remove(jsonObject);
        jsonObject.put("indexCode",data.getString("indexCode"));
        dataArray.add(indexNum,jsonObject);
        dataJson.put("data",dataArray);
        fileService.writeJSONObject(dataJson);
    }

    public void deleteData(int indexNum) throws IOException {
        JSONObject dataJson = fileService.readJSONObject();
        JSONArray dataArray = dataJson.getJSONArray("data");
        JSONObject jsonObject = dataArray.getJSONObject(indexNum);
        dataArray.remove(jsonObject);
        for(int i = indexNum;i < dataArray.size(); i ++){
            JSONObject data = dataArray.getJSONObject(i);
            dataArray.remove(data);
            data.put("indexNum",i);
            dataArray.add(i,data);
        }
        dataJson.put("data",dataArray);
        fileService.writeJSONObject(dataJson);
    }


    public Result<JSONObject> getDataJSONObject() throws IOException{
        JSONObject dataJson = fileService.readJSONObject();
        return Result.success(dataJson);
    }

}
