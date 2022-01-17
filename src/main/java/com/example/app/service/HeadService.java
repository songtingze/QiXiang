package com.example.app.service;

import com.example.app.dao.IHeadDao;
import com.example.app.dao.IUserDao;
import com.example.app.entity.Head;

import com.example.app.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class HeadService {
    @Resource
    private IHeadDao headDao;


    public Head queryByHid(String hid) {
        return headDao.queryByHid(hid);
    }

    public void addHead(Head head) {
        headDao.addHead(head);
    }

    public void updateHead(Head head) {
        headDao.updateHead(head);
    }



}

