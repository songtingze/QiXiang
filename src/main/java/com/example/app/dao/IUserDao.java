package com.example.app.dao;

import com.example.app.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("userDao")
public interface IUserDao {
    User queryByUid(String uid);
    User queryByPhone(String phone);
    void addUser(User user);
    void updateUser(User user);
}
