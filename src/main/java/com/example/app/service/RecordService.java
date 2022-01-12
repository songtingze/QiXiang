package com.example.app.service;

import com.example.app.dao.IRecordDao;
import com.example.app.entity.Record;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RecordService {
    @Resource
    private IRecordDao recordDao;

    public Record queryByRid(String rid) {return recordDao.queryByRid(rid);}

    public void addRecord (Record record) {
        recordDao.addRecord(record);
    }

    public void deleteRecord (Record record){ recordDao.deleteRecord(record);}

    public void updateRecord(Record record) {recordDao.updateRecord(record);}
}