package com.example.app.controller.record;

import com.example.app.common.Result;
import com.example.app.entity.Record;
import com.example.app.entity.User;
import com.example.app.service.RecordService;
import com.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.UUID;

@RestController
@RequestMapping("/record")

public class RecordController {
    @Autowired
    private RecordService recordService;

    @PostMapping("/createRecord")
    public Result<Record> createSingleRecord(@RequestBody Record newRecord){

        //生成唯一标识rid
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        newRecord.setRid("R_" + uuid.substring(0,10));
        //创建
        Timestamp t = new Timestamp(System.currentTimeMillis());
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t);
        newRecord.setCreatetime(createTime);
        recordService.addRecord(newRecord);
        return Result.success(newRecord);

    }

    @PostMapping("/updateRecord")
    public Result<Record> updateSingleRecord(@RequestBody Record newRecord){
        Record record = recordService.queryByRid(newRecord.getRid());
        //修改日志
        Timestamp t = new Timestamp(System.currentTimeMillis());
        String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t);
        record.setUpdatetime(updateTime);
        record.setContent(newRecord.getContent());
        recordService.updateRecord(record);
        return Result.success(record);
    }

    @PostMapping("/deleteRecord")
    public Result<Record> deleteSingleRecord(@RequestBody Record newRecord){
        //删除
        Record record = recordService.queryByRid(newRecord.getRid());
        recordService.deleteRecord(record);
        return Result.success();
    }
}
