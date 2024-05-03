package com.example.app.repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.app.common.Result;
import com.example.app.service.FileService;
import com.example.app.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DataRepository {
    @Autowired
    private FileService fileService;

    @Autowired
    private IndexService indexService;

    public Result<String> getData(JSONObject jsonObject,String index_) throws IOException {
        int code = jsonObject.getIntValue("code");

        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String times = dateFormat.format(new Date());

        String msg = "";
        if (code == 0) {
            JSONArray dataStr = jsonObject.getJSONArray("data");
            JSONObject dataJson = fileService.readJSONObject();
            JSONArray dataArray = dataJson.getJSONArray("data");

            if(index_.equalsIgnoreCase("VIS_HOR_1MI")){
                fileService.writeSearchInfoTxt(times+"-成功-SURF_CHN_OTHER_MIN-查询指标如下:"+
                        indexService.queryByIndexCode(index_).getString("indexName")+"。\n");
            }else{
                String indexName = "";
                String[] indexes = index_.split(",");
                for(int i = 0;i < indexes.length;i ++){
                    indexName += indexService.queryByIndexCode(indexes[i]).getString("indexName");
                    if(i != indexes.length-1){
                        indexName += ";";
                    }
                }
                fileService.writeSearchInfoTxt(times+"-成功-SURF_CHN_MAIN_MIN-查询指标如下:"+
                        indexName+"。\n");
            }


            for(int i = 0 ; i < dataStr.size();i ++){
                JSONObject data = dataStr.getJSONObject(i);
                JSONObject index = indexService.queryByIndexCode(data.getString("indexCode"));
                if(index != null){
                    JSONObject dataObject = dataArray.getJSONObject(index.getInteger("indexNum"));
                    dataArray.remove(dataObject);
                    dataObject.put("data",data.getString("data"));
                    if(index.getString("indexStatus").equalsIgnoreCase("yes")){
                        if(data.getString("data") == null || data.getString("data").equalsIgnoreCase("999999") ||
                                data.getString("data").equalsIgnoreCase("")){
                            dataObject.put("data","缺值");
                            dataObject.put("dataStatus","lack");
                        }else if(index.getString("indexJudge").equalsIgnoreCase("lack")){
                            dataObject.put("dataStatus","normal");
                        }
                        else{
                            SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
                            String expression = "";
                            if(index.getString("indexJudge").equalsIgnoreCase("[]")){
                                String[] strs = index.getString("indexData").split(",");
                                expression = data.getString("data")+">="+strs[0]+" && "+data.getString("data")+"<="+strs[1];
                            }else{
                                expression = data.getString("data")+index.getString("indexJudge")+index.getString("indexData");
                            }
                            if(spelExpressionParser.parseExpression(expression).getValue(Boolean.class)){
                                dataObject.put("dataStatus","normal");
                            }else{
                                dataObject.put("dataStatus","abnormal");
                            }

                        }
                    }
                    dataArray.add(index.getInteger("indexNum"),dataObject);
                }
            }
            dataJson.put("data",dataArray);
            JSONObject dataTime = dataJson.getJSONObject("dataTime");
            dataTime.put("time",times);
            dataJson.put("dataTime",dataTime);
            fileService.writeJSONObject(dataJson);
            msg = "数据获取完成！";
        }else{
            msg = jsonObject.getString("message");

            if(index_.equalsIgnoreCase("VIS_HOR_1MI")){
                fileService.writeSearchInfoTxt(times+"-失败-SURF_CHN_OTHER_MIN-查询指标如下:"+
                        indexService.queryByIndexCode(index_).getString("indexName")+";失败原因："+msg+"。\n");
            }else{
                String indexName = "";
                String[] indexes = index_.split(",");
                for(int i = 0;i < indexes.length;i ++){
                    indexName += indexService.queryByIndexCode(indexes[i]).getString("indexName");
                    if(i != indexes.length-1){
                        indexName += ";";
                    }
                }
                fileService.writeSearchInfoTxt(times+"-失败-SURF_CHN_MAIN_MIN-查询指标如下:"+
                        indexName+";失败原因："+msg+"。\n");
            }

        }

        return Result.success(msg);
    }

    public Result<String> getWarningInfo() throws IOException {
        JSONObject dataJson = fileService.readJSONObject();
        JSONArray dataArray = dataJson.getJSONArray("data");
        String warningInfo = "";
        for(int i = 0; i < dataArray.size();i ++){
            JSONObject jsonObject = dataArray.getJSONObject(i);
            if(jsonObject.getString("dataStatus").equalsIgnoreCase("abnormal")){
                if(!warningInfo.equalsIgnoreCase("")){
                    warningInfo += ";";
                }
                JSONObject index = indexService.queryByIndexCode(jsonObject.getString("indexCode"));
                warningInfo += index.getString("indexName") + "数据异常";
            }
            if(jsonObject.getString("dataStatus").equalsIgnoreCase("lack")){
                if(!warningInfo.equalsIgnoreCase("")){
                    warningInfo += ";";
                }
                JSONObject index = indexService.queryByIndexCode(jsonObject.getString("indexCode"));
                warningInfo += index.getString("indexName") + "数据缺失";
            }
        }
        if(!warningInfo.equalsIgnoreCase("")){
            warningInfo += "。";
            return Result.success(warningInfo);
        }else {
            return Result.error("101","数据一切正常");
        }

    }
}
