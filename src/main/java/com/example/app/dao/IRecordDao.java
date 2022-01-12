package com.example.app.dao;

import com.example.app.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("recordDao")
public interface IRecordDao {

    void addRecord(Record record);
    void updateRecord(Record record);
    void deleteRecord(Record record);

    Record queryByRid(String rid);


}
