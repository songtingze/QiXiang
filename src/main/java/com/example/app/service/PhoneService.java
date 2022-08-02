package com.example.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.app.common.Result;
import com.example.app.entity.Index;
import com.example.app.entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PhoneService {
    @Autowired
    private FileService fileService;

    public boolean isMobileNO(String mobile){
        if (mobile.length() != 11)
        {
            return false;
        }else{
            /**
             * 移动号段正则表达式
             */
            String pat1 = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
            /**
             * 联通号段正则表达式
             */
            String pat2  = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$";
            /**
             * 电信号段正则表达式
             */
            String pat3  = "^((133)|(153)|(177)|(18[0,1,9])|(149))\\d{8}$";
            /**
             * 虚拟运营商正则表达式
             */
            String pat4 = "^((170))\\d{8}|(1718)|(1719)\\d{7}$";

            Pattern pattern1 = Pattern.compile(pat1);
            Matcher match1 = pattern1.matcher(mobile);
            boolean isMatch1 = match1.matches();
            if(isMatch1){
                return true;
            }
            Pattern pattern2 = Pattern.compile(pat2);
            Matcher match2 = pattern2.matcher(mobile);
            boolean isMatch2 = match2.matches();
            if(isMatch2){
                return true;
            }
            Pattern pattern3 = Pattern.compile(pat3);
            Matcher match3 = pattern3.matcher(mobile);
            boolean isMatch3 = match3.matches();
            if(isMatch3){
                return true;
            }
            Pattern pattern4 = Pattern.compile(pat4);
            Matcher match4 = pattern4.matcher(mobile);
            boolean isMatch4 = match4.matches();
            if(isMatch4){
                return true;
            }
            return false;
        }
    }

    public Result<String> addPhone(JSONObject jsonObject) throws IOException {
        if(jsonObject.getString("phone").equalsIgnoreCase("") || jsonObject.getString("phone") == null){
            return Result.error("101","手机号不能为空！");
        }
        if(jsonObject.getString("remark").equalsIgnoreCase("") || jsonObject.getString("remark") == null){
            return Result.error("101","备注不能为空！");
        }
        if(isMobileNO(jsonObject.getString("phone"))){
            JSONArray jsonArray = fileService.readPhoneJSONArray();
            for(int i = 0 ;i < jsonArray.size(); i++){
                JSONObject json = jsonArray.getJSONObject(i);
                if(json.getString("phone").equalsIgnoreCase(jsonObject.getString("phone"))){
                    return Result.error("101","输入手机号已存在！");
                }
            }
            jsonObject.put("seq",jsonArray.size());
            jsonArray.add(jsonObject);
            fileService.writePhoneJSONArray(jsonArray);

            return Result.success("手机号添加成功！");
        }
        else{
            return Result.error("101","输入手机号不合法！");
        }

    }
    //修改指标
    public Result<String> modifyPhone(JSONObject jsonObject) throws IOException{
        if(isMobileNO(jsonObject.getString("phone"))){
            JSONArray jsonArray = fileService.readPhoneJSONArray();
            int phoneSeq = jsonObject.getInteger("seq");
            for(int i = 0 ;i < jsonArray.size(); i++){
                JSONObject json = jsonArray.getJSONObject(i);
                if(json.getString("phone").equalsIgnoreCase(jsonObject.getString("phone"))&& i!=phoneSeq){
                    return Result.error("101","输入手机号已存在！");
                }
            }
            JSONObject phone = jsonArray.getJSONObject(phoneSeq);
            jsonArray.remove(phone);
            phone.put("phone",jsonObject.getString("phone"));
            phone.put("status",jsonObject.getString("status"));
            phone.put("remark",jsonObject.getString("remark"));
            jsonArray.add(phoneSeq,phone);
            fileService.writePhoneJSONArray(jsonArray);
            return Result.success("手机号修改成功！");
        }
        else{
            return Result.error("101","输入手机号不合法！");
        }

    }

    public Result<String> deletePhone(int phoneSeq) throws IOException {
        JSONArray jsonArray = fileService.readPhoneJSONArray();
        JSONObject jsonObject = jsonArray.getJSONObject(phoneSeq);
        jsonArray.remove(jsonObject);
        for(int i = phoneSeq;i < jsonArray.size(); i ++){
            JSONObject phone = jsonArray.getJSONObject(i);
            jsonArray.remove(phone);
            phone.put("seq",i);
            jsonArray.add(i,phone);
        }
        fileService.writePhoneJSONArray(jsonArray);
        return Result.success("手机号删除成功！");
    }

    public Result<List<Phone>> queryAllPhone() throws IOException {
        JSONArray jsonArray = fileService.readPhoneJSONArray();
        List<Phone> phoneList = new ArrayList();
        for(int i = 0;i < jsonArray.size();i ++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Phone phone = new Phone(false,jsonObject.getString("seq"),jsonObject.getString("phone"),
                    jsonObject.getString("status"),jsonObject.getString("remark"));
            phoneList.add(phone);
        }
        return Result.success(phoneList);
    }

    public Result<String> getPhones() throws IOException{
        JSONArray jsonArray = fileService.readPhoneJSONArray();
        String phones = "";
        for(int i = 0;i < jsonArray.size();i ++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(jsonObject.getString("status").equalsIgnoreCase("yes")){
                if(phones.equalsIgnoreCase("")){
                    phones += jsonObject.getString("phone");
                }else {
                    phones += ","+jsonObject.getString("phone");
                }
            }
        }
        if(phones.equalsIgnoreCase("")){
            return Result.error("101","无手机号");
        }else {
            return Result.success(phones);
        }
    }

}
