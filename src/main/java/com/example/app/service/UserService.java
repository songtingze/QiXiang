package com.example.app.service;

import com.example.app.common.Result;
import com.example.app.config.RedisService;
import com.example.app.dao.IUserDao;
import com.example.app.entity.Head;
import com.example.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {
    @Resource
    private IUserDao userDao;
    @Autowired
    private BCryptPasswordEncoder encoding;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private HeadService headService;


    @Value("${head.uploadPath}")
    private String uploadPath;

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

    //用户登录
    public Result<User> login(String phone,String password){
        try{
            User user = queryByPhone(phone);
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

    //用户注册
    public Result<User> createSingleUser(User newUser){
        try {
            User user = queryByPhone(newUser.getPhone());
            //获取缓存的验证码
//            Object code = redisService.getValue(newUser.getPhone());
            //判断手机号是否已被注册
            if(user != null){
                return Result.error("103","手机已被注册");
            }
            //验证码判断
//            else if(code == null){
//                return Result.error("104","验证码失效");
//            }else if(!code.toString().equalsIgnoreCase(newUser.getVerifyCode())){
//                return Result.error("105","验证码错误");
//            }
            else{
                //生成唯一标识uid
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                newUser.setUid("U_" + uuid.substring(0,10));
                //密码加密
                newUser.setPassword(encoding.encode(newUser.getPassword()));
                //设置创建时间
                Timestamp t = new Timestamp(System.currentTimeMillis());
                String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t);
                newUser.setCreateTime(createTime);
                addUser(newUser);
                return Result.success(newUser);
            }
        }catch (Exception e){

            e.printStackTrace();
            return null;
        }
    }
    //修改用户头像
    public Result<User> modifyHead(String uid,String imgData){
        try {
            String folderName =  new SimpleDateFormat("yyyyMMdd").format(new Date());
            String file_name = new SimpleDateFormat("HHmmss").format(new Date()) + ".jpg";
            String path = uploadPath + folderName + "/" ;
            File file = new File(path, file_name);
            //判断文件夹是否存在，不存在就创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            baseService.base64ConvertToPicture(imgData,path + file_name);

            //把base64转成图片，保存到指定路径
            baseService.scale(path + file_name,"jpg");

            Head head = new Head();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            head.setHid("H_" + uuid.substring(0,10));
            head.setPath(folderName + "/" + file_name);
            headService.addHead(head);

            User user = userDao.queryByUid(uid);
            System.out.println(user);
            user.setHid(head.getHid());
            Timestamp t = new Timestamp(System.currentTimeMillis());
            String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t);
            user.setUpdateTime(updateTime);
            userDao.updateUser(user);
            return Result.success(queryByUid(uid));
        }catch (Exception e){

            e.printStackTrace();
            return null;
        }

    }
}