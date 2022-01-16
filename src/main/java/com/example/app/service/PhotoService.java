package com.example.app.service;

import com.example.app.dao.IPhotoDao;
import com.example.app.dao.IUserDao;
import com.example.app.entity.Photo;

import com.example.app.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class PhotoService {
    @Resource
    private IPhotoDao photoDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private BaseService BaseService;

    @Value("${photo.uploadPath}")
    private String uploadPath;

    public Photo queryByPid(String pid) {
        return photoDao.queryByPid(pid);
    }

    public void addPhoto(Photo photo) {
        photoDao.addPhoto(photo);
    }

    public void updatePhoto(Photo photo) {
        photoDao.updatePhoto(photo);
    }

    //上传图片到服务器
    public void uploadPhoto(String uid,String imgStr){

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

        Photo photo = new Photo();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        photo.setPid("P_" + uuid.substring(0,10));
        photo.setPath(folderName + "/" + file_name);
        addPhoto(photo);

        User user = userDao.queryByUid(uid);
        user.setPid(photo.getPid());
        userDao.updateUser(user);

    }

    //下载图片
    public void downloadPhoto(String photoPath){
        String base64Str = BaseService.pictureConvertToBase64(photoPath);
        System.out.println(base64Str);
    }

}

