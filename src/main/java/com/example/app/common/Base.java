package com.example.app.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Base {
    public String getTimes(){
        Date now = new Date();
        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(now);
// 把日期往后增加一天,整数  往后推,负数往前移动
//        calendar.add(calendar.DATE, -1);
// 这个时间就是日期往后推一天的结果
        now=calendar.getTime();
        Date date = new Date(now.getTime() - 32400000);
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMddHHmm");
        String times = dateFormat.format(date)+"00";
        System.out.println(times);
        return times;
    }
}
