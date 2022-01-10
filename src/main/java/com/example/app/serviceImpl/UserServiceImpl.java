package com.example.app.serviceImpl;

import com.example.app.dao.IUserDao;
import com.example.app.entity.User;
import com.example.app.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private IUserDao userDao;

    @Override
    public User queryByUid(String uid) {
        return userDao.queryByUid(uid);
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}