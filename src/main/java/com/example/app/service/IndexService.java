package com.example.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.app.common.Result;
import com.example.app.entity.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class IndexService {

    @Autowired
    private FileService fileService;


    //判断指标数据是否为数字
    public boolean isNumeric(String indexData){
        Pattern pattern = Pattern.compile("[0-9]*");
        if(indexData.startsWith("-")){
            indexData = indexData.replace("-","");
        }
        if(indexData.indexOf(".")>0){//判断是否有小数点
            if(indexData.indexOf(".")==indexData.lastIndexOf(".") && indexData.split("\\.").length==2){ //判断是否只有一个小数点
                return pattern.matcher(indexData.replace(".","")).matches();
            }else {
                return false;
            }
        } else {
            return pattern.matcher(indexData).matches();
        }
    }

    //判断指标是否合规
    public String isIndexRight(JSONObject jsonObject) throws IOException {
        String indexName = jsonObject.getString("indexName");
        String indexCode = jsonObject.getString("indexCode");
        String indexData = jsonObject.getString("indexData");
        String indexJudge = jsonObject.getString("indexJudge");
        String msg = "";
        if(indexName.equalsIgnoreCase("") || indexName == null){
            msg = "气象指标名称不能为空!";
            return msg;
        }
        if(indexCode.equalsIgnoreCase("") || indexCode == null){
            msg = "气象指标代码不能为空!";
            return msg;
        }
        if(indexData.equalsIgnoreCase("") || indexData == null){
            if(!indexJudge.equalsIgnoreCase("lack")){
                msg = "气象指标临界值不能为空!";
                return msg;
            }
        }
        JSONArray jsonArray = fileService.readJSONArray();
        for(int i = 0; i< jsonArray.size();i ++){
            JSONObject index = jsonArray.getJSONObject(i);
            if(indexName.equalsIgnoreCase(index.getString("indexName"))){
                if(jsonObject.get("indexNum")!=null && jsonObject.getIntValue("indexNum") == i){

                }else{
                    msg = "该气象指标名称已存在!";
                    return msg;
                }
            }
            if(indexCode.equalsIgnoreCase(index.getString("indexCode"))){
                if(jsonObject.get("indexNum")!=null && jsonObject.getIntValue("indexNum") == i){

                }else{
                    msg = "该气象指标代码已存在!";
                    return msg;
                }
            }
        }
        if(indexJudge.equalsIgnoreCase("[]")){
            String[] strs = indexData.split(",");
            if(strs.length != 2 || !isNumeric(strs[0]) || !isNumeric(strs[1])){
                msg = "请输入正确的范围格式！如2,3";
                return msg;
            }
        }else{
            if(!isNumeric(indexData)){
                msg = "输入的指标临界值是非法数字!";
                return msg;
            }
        }
        msg = "success";
        return msg;
    }

    //根据指标号码查询指标
    public Result<JSONObject> queryByIndexNum(int indexNum) throws IOException {
        JSONArray jsonArray = fileService.readJSONArray();
        JSONObject jsonObject = jsonArray.getJSONObject(indexNum);
        return Result.success(jsonObject);
    }

    //返回所有指标信息
    public Result<List<Index>> queryAllIndex() throws IOException {
        JSONArray jsonArray = fileService.readJSONArray();
        List<Index> indexList = new ArrayList();
        for(int i = 0;i < jsonArray.size();i ++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Index index = new Index(false,jsonObject.getString("indexCode"),jsonObject.getString("indexData"),
                    jsonObject.getString("indexJudge"),jsonObject.getString("indexName"),jsonObject.getString("indexNum"),
                    jsonObject.getString("indexStatus"));
            indexList.add(index);
        }
        return Result.success(indexList);
    }

    //返回所有指标代码
    public String queryAllIndexCode() throws IOException {
        JSONArray jsonArray = fileService.readJSONArray();
        String indexCodes = "";
        for (int i = 0;i < jsonArray.size();i ++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(!jsonObject.getString("indexCode").equalsIgnoreCase("VIS_HOR_1MI")){
                indexCodes += jsonObject.getString("indexCode");
                if(i != jsonArray.size()-1){
                    indexCodes += ",";
                }
            }
        }
        return indexCodes;
    }
    public Boolean queryIndex(String indexCode) throws IOException {
        JSONArray jsonArray = fileService.readJSONArray();
        for (int i = 0;i < jsonArray.size();i ++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(jsonObject.getString("indexCode").equalsIgnoreCase(indexCode)){
                return true;
            }
        }
        return false;
    }

    //根据指标号码查询指标
    public JSONObject queryByIndexCode(String indexCode) throws IOException {
        JSONArray jsonArray = fileService.readJSONArray();
        for(int i = 0;i < jsonArray.size();i ++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(jsonObject.getString("indexCode").equalsIgnoreCase(indexCode)){
                return jsonObject;
            }
        }
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }


}
