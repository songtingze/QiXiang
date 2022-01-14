package com.example.app.dao;

import com.example.app.entity.Letter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("letterDao")
public interface ILetterDao {

    Letter queryByLid(String lid);
    void addLetter(Letter letter);
    List<Letter> queryByReceiveUid(String receiveUid);
}
