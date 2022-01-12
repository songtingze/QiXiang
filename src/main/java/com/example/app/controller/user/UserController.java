package com.example.app.controller.user;

import com.example.app.common.Result;
import com.example.app.entity.User;
import com.example.app.service.UserService;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;



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
        try{
            User user = userService.queryByPhone(phone);
            //用户是否存在
            if(user == null){
                return Result.error("101","用户不存在");
            }else{
                //密码是否正确
                //密码解密
                if(encoding.matches(password,user.getPassword())){
                    return Result.success(user);
                }
                else{
                    return Result.error("102","密码错误");
                }
            }

        }catch (Exception e){

            e.printStackTrace();
            return null;
        }

    }

    @PostMapping("/register")
    public Result<User> createSingleUser(@RequestBody User newUser){
        try {
            User user = userService.queryByPhone(newUser.getPhone());
            //判断手机号是否已被注册
            if(user != null){
                return Result.error("103","手机已被注册");
            }else{
                //生成唯一标识uid
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                newUser.setUid("U_" + uuid.substring(0,10));
                //密码加密
                newUser.setPassword(encoding.encode(newUser.getPassword()));
                //设置创建时间
                Timestamp t = new Timestamp(System.currentTimeMillis());
                String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t);
                newUser.setCreateTime(createTime);
                userService.addUser(newUser);
                return Result.success(newUser);
            }
        }catch (Exception e){

            e.printStackTrace();
            return null;
        }

    }
    //发送短信验证码
    @PostMapping("/send")
    public Result sendMSM(@RequestParam String telephone) throws Exception {
        User user = userService.queryByPhone(telephone);
        //判断手机号是否已被注册
        if(user != null){
            return Result.error("103","手机已被注册");
        }else{
            //利用榛子云api发送短信
            String apiUrl = "http://sms_developer.zhenzikj.com";
            String appId = "107456";
            String appSecret = "bb3f909f-ef88-4343-8a19-9930e728a0f8";

            ZhenziSmsClient client = new ZhenziSmsClient(apiUrl,appId, appSecret);

            HashMap<String, Object> map = new HashMap<>();
            //这个是榛子云短信平台用户中心下的短信管理的短信模板的模板id
            map.put("templateId", "2705");
            //生成验证码
            int pow = (int) Math.pow(10, 4 - 1);

            String verificationCode = String.valueOf((int) (Math.random() * 9 * pow + pow));
            //随机生成messageId，验证验证码的时候，需要携带这个参数去取验证码
            String messageId = UUID.randomUUID().toString();
            map.put("messageId", messageId);
            String[] templateParams = new String[2];
            //两个参数分别为验证码和过期时间
            templateParams[0] = verificationCode;
//        templateParams[0] = "5211314";
            templateParams[1] = String.valueOf("五分钟");
            map.put("templateParams", templateParams);

            map.put("number", telephone);

            String result = client.send(map);

            JSONObject jsonObject = JSONObject.parseObject(result);
            int  code =(int)jsonObject.get("code");

            //发送成功
            if(code == 0){
                return Result.success();
            }else{
                //发送失败
                return Result.error("104","短信发送失败");
            }
        }

    }

}
