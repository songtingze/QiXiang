package com.example.app.common;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class SendMsg {
//    @SuppressWarnings({ "static-access", "static-access" })
//    public static void main(String[] args) {
////        try {
////            new SendMsg().sendSMS("15653408465","Java Http!!!!!","");
////        } catch (MalformedURLException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        } catch (UnsupportedEncodingException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
//        String[] strs = "1111".split(",");
//        System.out.println(strs.length);
//
//    }
    /*
     * 发送信息
     */
    public Result<String> sendSMS(String Mobile,String Content,String send_time) throws MalformedURLException, UnsupportedEncodingException {
        URL url = null;
        String CorpID="pyxqxj";//账号
        String Pwd="lrpy5471812";//密码
        String send_content=URLEncoder.encode(Content.replaceAll("<br/>", " "), "GBK");
        url = new URL("http://kegoo.hss100.cn/Interface/BatchSend2.aspx?CorpID="+CorpID+"&Pwd="+Pwd+"&Mobile="+Mobile+"&Content="+send_content+"&Cell=&SendTime="+send_time);
        BufferedReader in = null;
        int inputLine = 0;
        try {
//            System.out.println("手机号："+Mobile);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            inputLine = new Integer(in.readLine()).intValue();
            if(inputLine>0){
                return Result.success("发送成功");
            }else if(inputLine==-1){
                return Result.error("-2","账号未注册");
            }else if(inputLine==-2){
                return Result.error("-2","其他错误");
            }else if(inputLine==-3){
                return Result.error("-2","帐号或密码错误");
            }else if(inputLine==-4){
                return Result.error("-2","一次提交信息不能超过600个手机号码");
            }else if(inputLine==-5){
                return Result.error("-2","余额不足，请先充值");
            }else if(inputLine==-6){
                return Result.error("-2","定时发送时间不是有效的时间格式");
            }else if(inputLine==-8){
                return Result.error("-2","发送内容需在3到250字之间");
            }else if(inputLine==-9){
                return Result.error("-2","发送号码为空");
            }else if(inputLine==-100){
                return Result.error("-2","限制此IP访问");
            }else if(inputLine==-101){
                return Result.error("-2","调用接口速度太快");
            }else {
                return Result.error("-2","发送异常");
            }
        } catch (Exception e) {
//            System.out.println("发送异常");
            inputLine=-2;
            return Result.error("-2","发送异常");
        }
//        System.out.println("响应结果："+inputLine);
//        return inputLine;
    }


}
