package com.example.app.service;

import com.example.app.dao.IUserDao;
import com.example.app.entity.User;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private IUserDao userDao;

    public User queryByUid(String uid) {
        return userDao.queryByUid(uid);
    }

    public User queryByPhone(String phone) {
        return userDao.queryByPhone(phone);
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}