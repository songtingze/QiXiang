package com.example.app.service;

import com.example.app.dao.IPhotoDao;
import com.example.app.dao.IUserDao;
import com.example.app.entity.Photo;

import com.example.app.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class PhotoService {
    @Resource
    private IPhotoDao photoDao;
    @Resource
    private IUserDao userDao;

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

    public void uploadPhoto(String uid,String imgStr){

        String folderName =  new SimpleDateFormat("yyyyMMdd").format(new Date());
        String file_name = new SimpleDateFormat("HHmmss").format(new Date()) + ".jpg";
        String path = uploadPath + folderName + "/" ;
        File file = new File(path, file_name );
        //判断文件夹是否存在，不存在就创建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        base64ConvertToPicture(imgStr,path + file_name);

        //把base64转成图片，保存到指定路径
        scale(path + file_name,"jpg");

        Photo photo = new Photo();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        photo.setPid("P_" + uuid.substring(0,10));
        photo.setPath(folderName + "/" + file_name);
        addPhoto(photo);

        User user = userDao.queryByUid(uid);
        user.setPid(photo.getPid());
        userDao.updateUser(user);

    }
    public void scale(String srcImageFile,String type) {
        try {

            File tempFile = new File(srcImageFile);
            BufferedImage src = ImageIO.read(tempFile); // 读入文件

            int width = 960;
            int height = 544;

            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, type, new File(srcImageFile));// 输出到文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean base64ConvertToPicture(String imgStr,String path){
        if (imgStr == null){
            //图像数据为空
            return false;
        }

        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

}

