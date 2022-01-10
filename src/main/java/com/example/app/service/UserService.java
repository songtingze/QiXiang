package com.example.app.service;


import com.example.app.entity.User;

public interface UserService {
    User queryByUid(String uid);
    void addUser(User user);
    void updateUser(User user);
}