package com.example.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileService {

    @Value("${filePath.indexConfig}")
    private String indexFilePath;
    @Value("${filePath.dataConfig}")
    private String dataFilePath;

    @Autowired
    private DataService dataService;

    public JSONArray readJSONArray() throws IOException {
        //如果文件没有内容，则返回空的JsonArray
        File file = new File(indexFilePath);
        if(file.length() == 0){
            return new JSONArray();
        }
        FileInputStream fileInputStream = new FileInputStream(indexFilePath);
        JSONArray jsonArray = JSONArray.parseObject(fileInputStream, JSONArray.class);
        fileInputStream.close();
        return jsonArray;
    }
    public void writeJSONArray(JSONArray jsonArray) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(indexFilePath)),"utf-8"));
//        FileWriter fileWriter = new FileWriter(indexFilePath);
//        BufferedWriter bw = new BufferedWriter(fileWriter);
        writer.write(jsonArray.toString());
        writer.close();
    }

    public JSONObject readJSONObject() throws IOException{
        //如果文件没有内容，则返回空的JsonArray
        File file = new File(dataFilePath);
        if(file.length() == 0){
            dataService.initDataFile();
//            return new JSONObject();
        }
        FileInputStream fileInputStream = new FileInputStream(dataFilePath);
        JSONObject jsonObject = JSONObject.parseObject(fileInputStream, JSONObject.class);
        fileInputStream.close();
        return jsonObject;
    }

    public void writeJSONObject(JSONObject jsonObject) throws IOException{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(dataFilePath)),"utf-8"));
//        FileWriter fileWriter = new FileWriter(dataFilePath);
//        BufferedWriter bw = new BufferedWriter(fileWriter);
        writer.write(jsonObject.toString());
        writer.close();
    }
}
