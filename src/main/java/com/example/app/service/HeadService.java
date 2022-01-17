package com.example.app.service;

import com.example.app.dao.IHeadDao;
import com.example.app.dao.IUserDao;
import com.example.app.entity.Head;

import com.example.app.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class HeadService {
    @Resource
    private IHeadDao headDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private BaseService BaseService;

    @Value("${head.uploadPath}")
    private String uploadPath;

    public Head queryByHid(String hid) {
        return headDao.queryByHid(hid);
    }

    public void addHead(Head head) {
        headDao.addHead(head);
    }

    public void updateHead(Head head) {
        headDao.updateHead(head);
    }

    //上传头像到服务器
    public void uploadHead(String uid,String imgStr){

        String folderName =  new SimpleDateFormat("yyyyMMdd").format(new Date());
        String file_name = new SimpleDateFormat("HHmmss").format(new Date()) + ".jpg";
        String path = uploadPath + folderName + "/" ;
        File file = new File(path, file_name);
        //判断文件夹是否存在，不存在就创建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        BaseService.base64ConvertToPicture(imgStr,path + file_name);

        //把base64转成图片，保存到指定路径
        BaseService.scale(path + file_name,"jpg");

        Head head = new Head();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        head.setHid("H_" + uuid.substring(0,10));
        head.setPath(folderName + "/" + file_name);
        addHead(head);

        User user = userDao.queryByUid(uid);
        user.setHid(head.getHid());
        userDao.updateUser(user);

    }

    //从服务器下载头像到本地
    public void downloadHead(String headPath){
        String base64Str = BaseService.pictureConvertToBase64(headPath);
        System.out.println(base64Str);
    }

}

