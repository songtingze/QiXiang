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

    @Autowired
    private DataService dataService;

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
        String msg = "";
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
        if(!isNumeric(indexData)){
            msg = "该气象指标代码已存在!";
            return msg;
        }
        msg = "success";
        return msg;
    }

    //添加指标
    public Result<String> addIndex(JSONObject jsonObject) throws IOException {
        String msg = isIndexRight(jsonObject);
        if(msg.equalsIgnoreCase("success")){
            JSONArray jsonArray = fileService.readJSONArray();

            jsonObject.put("indexNum",jsonArray.size());
            JSONObject data = new JSONObject();
            data.put("dataNum",jsonArray.size());
            data.put("indexCode",jsonObject.getString("indexCode"));
            data.put("data","");
            data.put("dataStatus","UnKnown");
            jsonArray.add(jsonObject);
            fileService.writeJSONArray(jsonArray);
            dataService.addData(data);
            return Result.success("气象指标添加成功！");
        }
        else{
            return Result.error("101",msg);
        }

    }

    //修改指标
    public Result<String> modifyIndex(JSONObject index) throws IOException{
        String msg = isIndexRight(index);
        if(msg.equalsIgnoreCase("success")){
            JSONArray jsonArray = fileService.readJSONArray();
            int indexNum = index.getInteger("indexNum");
            JSONObject jsonObject = jsonArray.getJSONObject(indexNum);
            jsonArray.remove(jsonObject);
            jsonObject.put("indexName",index.getString("indexName"));
            jsonObject.put("indexCode",index.getString("indexCode"));
            jsonObject.put("indexData",index.getString("indexData"));
            jsonObject.put("indexJudge",index.getString("indexJudge"));
            jsonObject.put("indexStatus",index.getString("indexStatus"));
            jsonArray.add(indexNum,jsonObject);
            fileService.writeJSONArray(jsonArray);
            JSONObject data = new JSONObject();
            data.put("dataNum",indexNum);
            data.put("indexCode",index.getString("indexCode"));
            dataService.modifyData(data);
            return Result.success("气象指标修改成功！");
        }
        else{
            return Result.error("101",msg);
        }

    }

    //删除指标
    public Result<String> deleteIndex(int indexNum) throws IOException {
        JSONArray jsonArray = fileService.readJSONArray();
        JSONObject jsonObject = jsonArray.getJSONObject(indexNum);
        jsonArray.remove(jsonObject);
        for(int i = indexNum;i < jsonArray.size(); i ++){
            JSONObject index = jsonArray.getJSONObject(i);
            jsonArray.remove(index);
            index.put("indexNum",i);
            jsonArray.add(i,index);
        }
        fileService.writeJSONArray(jsonArray);
        dataService.deleteData(indexNum);
        return Result.success("气象指标删除成功！");
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
            indexCodes += jsonObject.getString("indexCode");
            if(i != jsonArray.size()-1){
                indexCodes += ",";
            }
        }
        return indexCodes;
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
