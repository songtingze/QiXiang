package com.example.app.common;

import java.util.Calendar;

import java.util.Date;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

//启动系统定时器，每隔1分钟/指定时间执行任务

//java.util.Timer定时器是以后台线程方式控制运行，它是线程安全，无需手工加锁

    public static void main(String[] args) {

//创建定时器

        Timer timer = new Timer();

//指定每1秒钟执行一次

        /*

         * 参数一：任务类对象

         * 参数二：第一次执行前的延迟时间，单位毫秒

         * 参数三：每隔毫秒时间执行一次

         */

timer.schedule(new MyTimerTask(),1000,60000);

//指定时间执行一次

//        Calendar c = Calendar.getInstance();
//
//        c.set(2018,10,6,10,30,40);

//        timer.schedule(new MyTimerTask(),c.getTime());

    }

}

//线程任务

class MyTimerTask extends TimerTask{

    public void run() {
//
//    Base base = new Base();
//    base.getTimes();
        StaElemSearchAPI_CLIB_callAPI_to_array2D staElemSearchAPI_clib_callAPI_to_array2D = new StaElemSearchAPI_CLIB_callAPI_to_array2D();
        staElemSearchAPI_clib_callAPI_to_array2D.test();

    }

}
