package com.example.app.controller.user;

import com.example.app.common.Result;
import com.example.app.entity.User;
import com.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoding;

    //用户登录
    @PostMapping("/login")
    public Result<User> login(@RequestParam String phone, @RequestParam String password){
        User user = userService.queryByPhone(phone);
        //用户是否存在
        if(user == null){
            return Result.error("101","用户不存在");
        }else{
            //密码是否正确
            if(encoding.matches(password,user.getPassword())){
                return Result.success(user);
            }
            else{
                return Result.error("102","密码错误");
            }
        }
    }

    @PostMapping("/register")
    public Result<User> createSingleUser(@RequestBody User newUser){
        User user = userService.queryByPhone(newUser.getPhone());
        if(user != null){
            return Result.error("103","手机已被注册");
        }else{
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            newUser.setUid(uuid);
            newUser.setPassword(encoding.encode(newUser.getPassword()));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            newUser.setCreateTime(formatter.format(new Date()));
            userService.addUser(newUser);
            return Result.success(newUser);
        }
    }


}
