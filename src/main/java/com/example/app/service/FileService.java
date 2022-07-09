package com.example.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileService {

    @Value("${filePath.indexConfig}")
    private String indexFilePath;
    @Value("${filePath.dataConfig}")
    private String dataFilePath;

    public JSONArray readJSONArray() throws IOException {
        //如果文件没有内容，则返回空的JsonArray
        File file = new File(indexFilePath);
        if(file.length() == 0){
            return new JSONArray();
        }
        JSONArray jsonArray = JSONArray.parseObject(new FileInputStream(indexFilePath), JSONArray.class);
        return jsonArray;
    }
    public void writeJSONArray(JSONArray jsonArray) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(indexFilePath));
        bw.write(jsonArray.toString());
        bw.close();
    }

    public JSONObject readJSONObject() throws IOException{
        //如果文件没有内容，则返回空的JsonArray
        File file = new File(dataFilePath);
        if(file.length() == 0){
            return new JSONObject();
        }
        JSONObject jsonObject = JSONObject.parseObject(new FileInputStream(dataFilePath), JSONObject.class);
        return jsonObject;
    }

    public void writeJSONObject(JSONObject jsonObject) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(dataFilePath));
        bw.write(jsonObject.toString());
        bw.close();
    }

}
