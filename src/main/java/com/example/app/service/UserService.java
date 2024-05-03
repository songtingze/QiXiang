package com.example.app.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private FileService fileService;

    public void modifyUserInfo(JSONObject jsonObject) throws IOException {
        fileService.writeUserJSONObject(jsonObject);
    }

    public JSONObject getUserInfo() throws IOException {
        return fileService.readUserJSONObject();
    }

}
