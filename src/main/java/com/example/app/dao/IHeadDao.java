package com.example.app.dao;

import com.example.app.entity.Head;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("headDao")
public interface IHeadDao {
    Head queryByHid(String hid);
    void addHead(Head head);
    void updateHead(Head head);
}
