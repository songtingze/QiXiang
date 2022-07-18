package com.example.app.common;

import com.alibaba.fastjson.JSONObject;
import com.example.app.service.DataService;
import com.example.app.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SimpleSchedule {

//    @Autowired
//    private IndexService indexService;
//
//    @Autowired
//    private DataService dataService;
//
//    private Integer time = 0;
//    @Scheduled(cron = "*/10 * * * * ?")   //定时器定义，设置执行时间
//    private void process() {
//        System.out.println("定时器1执行"+time++);
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//        System.out.println(format.format(new Date()));
//        StaElemSearchAPI_CLIB_callAPI_to_array2D staElemSearchAPI_clib_callAPI_to_array2D = new StaElemSearchAPI_CLIB_callAPI_to_array2D();
//        try {
//            System.out.println(indexService.queryAllIndexCode());
//            JSONObject jsonObject = staElemSearchAPI_clib_callAPI_to_array2D.test(indexService.queryAllIndexCode());
//            dataService.getData(jsonObject);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}

