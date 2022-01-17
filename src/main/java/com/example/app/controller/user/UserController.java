package com.example.app.controller.user;

import com.example.app.common.Result;
import com.example.app.entity.User;
import com.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    //用户登录
    @PostMapping("/login")
    public Result<User> login(@RequestParam String phone, @RequestParam String password){
        try{
            return userService.login(phone,password);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    //注册
    @PostMapping("/register")
    public Result<User> createSingleUser(@RequestBody User newUser){
        try {
            return userService.createSingleUser(newUser);
        }catch (Exception e){

            e.printStackTrace();
            return null;
        }

    }

    //修改头像
    @PostMapping("/modifyHead")
    public Result<User> modifyHead(@RequestParam String uid,@RequestParam String imgData){
        try {
            return userService.modifyHead(uid,imgData);
        }catch (Exception e){

            e.printStackTrace();
            return null;
        }
    }


//    //发送短信验证码
//    @PostMapping("/send")
//    public Result<String> sendMSM(@RequestParam String telephone) throws Exception {
//        User user = userService.queryByPhone(telephone);
//        //判断手机号是否已被注册
//        if(user != null){
//            return Result.error("103","手机已被注册");
//        }else{
//            //利用榛子云api发送短信
//            String apiUrl = "http://sms_developer.zhenzikj.com";
//            String appId = "107456";
//            String appSecret = "bb3f909f-ef88-4343-8a19-9930e728a0f8";
//
//            ZhenziSmsClient client = new ZhenziSmsClient(apiUrl,appId, appSecret);
//
//            HashMap<String, Object> map = new HashMap<>();
//            //这个是榛子云短信平台用户中心下的短信管理的短信模板的模板id
//            map.put("templateId", "2705");
//            //生成验证码
//            int pow = (int) Math.pow(10, 4 - 1);
//
//            String verificationCode = String.valueOf((int) (Math.random() * 9 * pow + pow));
//            //随机生成messageId，验证验证码的时候，需要携带这个参数去取验证码
//            String messageId = UUID.randomUUID().toString();
//            map.put("messageId", messageId);
//            String[] templateParams = new String[2];
//            //两个参数分别为验证码和过期时间
//            templateParams[0] = verificationCode;
////        templateParams[0] = "5211314";
//            templateParams[1] = String.valueOf("五分钟");
//            map.put("templateParams", templateParams);
//
//            map.put("number", telephone);
//
//            String result = client.send(map);
//
//            JSONObject jsonObject = JSONObject.parseObject(result);
//            int  code =(int)jsonObject.get("code");
//
//            //发送成功
//            if(code == 0){
//                redisService.setKey(telephone,verificationCode);
//                return Result.success(verificationCode+"");
//            }else{
//                //发送失败
//                return Result.error("104","短信发送失败");
//            }
//        }
//
//    }
//
//    @PostMapping("/getRedis")
//    public Result<String> getRedis(@RequestParam String telephone){
//        Object code = redisService.getValue(telephone);
//        if(code != null){
//            return Result.success(code.toString());
//        }else{
//            return Result.error("111","验证码失效");
//        }
//    }



}
