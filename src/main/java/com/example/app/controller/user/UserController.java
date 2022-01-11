package com.example.app.controller.user;

import com.example.app.common.Result;
import com.example.app.entity.User;
import com.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //用户登录
    @PostMapping("/login")
    public Result<User> login(@RequestParam String phone, @RequestParam String password){
        User user = userService.queryByPhone(phone);
        //用户是否存在
        if(user == null){
            return Result.error("101","用户不存在");
        }else{
            //密码是否正确
            if(user.getPassword().equalsIgnoreCase(password)){
                return Result.success(user);
            }
            else{
                return Result.error("102","密码错误");
            }
        }
    }

}
