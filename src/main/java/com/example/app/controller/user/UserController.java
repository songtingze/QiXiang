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

    @PostMapping("/login")
    public Result<User> login(@RequestParam String uid, @RequestParam String password){
        User user = userService.queryByUid(uid);
        if(user.getPassword().equalsIgnoreCase(password)){
            return Result.success(user);
        }
        else{
            return Result.error("404","密码错误");
        }
    }

}
