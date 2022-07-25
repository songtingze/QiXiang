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

    public Result<String> getData(JSONObject jsonObject) throws IOException {
        int code = jsonObject.getIntValue("code");
        String msg = "";
        if (code == 0) {
            JSONArray dataStr = jsonObject.getJSONArray("data");
            JSONObject dataJson = fileService.readJSONObject();
            JSONArray dataArray = dataJson.getJSONArray("data");
            for(int i = 0 ; i < dataStr.size();i ++){
                JSONObject data = dataStr.getJSONObject(i);
                JSONObject index = indexService.queryByIndexCode(data.getString("indexCode"));
                if(index != null){
                    JSONObject dataObject = dataArray.getJSONObject(index.getInteger("indexNum"));
                    dataArray.remove(dataObject);
                    dataObject.put("data",data.getString("data"));
                    if(index.getString("indexStatus").equalsIgnoreCase("yes")){
                        if(data.getString("data") == null || data.getString("data").equalsIgnoreCase("999999")){
                            dataObject.put("data","缺值");
                            dataObject.put("dataStatus","lack");
                        }else{
                            SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
                            String expression = data.getString("data")+index.getString("indexJudge")+index.getString("indexData");
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
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            String times = dateFormat.format(new Date());
            dataTime.put("time",times);
            dataJson.put("dataTime",dataTime);
            fileService.writeJSONObject(dataJson);
            msg = "数据获取完成！";
        }else{
            msg = jsonObject.getString("message");
        }

        return Result.success(msg);
    }
}
