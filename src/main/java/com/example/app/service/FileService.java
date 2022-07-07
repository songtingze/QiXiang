package com.example.app.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileService {

    @Value("${filePath.dataConfig}")
    private String filePath;

    public JSONArray readFile() throws IOException {
        //如果文件没有内容，则返回空的JsonArray
        File file = new File(filePath);
        if(file.length() == 0){
            return new JSONArray();
        }
        JSONArray jsonArray = JSONArray.parseObject(new FileInputStream(filePath), JSONArray.class);
        return jsonArray;
    }
    public void writeFile(JSONArray jsonArray) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        bw.write(jsonArray.toString());
        bw.close();
    }

}
