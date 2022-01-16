package com.example.app.service;

import com.example.app.common.Result;
import com.example.app.dao.ILetterDao;
import com.example.app.entity.Letter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;


@Service
public class LetterService {
    @Resource
    private ILetterDao letterDao;

    public Letter queryByLid(String lid) {
        return letterDao.queryByLid(lid);
    }


    public void addLetter(Letter letter) {
        letterDao.addLetter(letter);
    }

    //获取所有信件
    public Result<PageInfo<Letter>> queryReceiveUid(Integer pageNum, Integer pageSize ,String receiveUid)
    {
        PageHelper.startPage(pageNum, pageSize); // 设定当前页码，以及当前页显示的条数
        //PageHelper.offsetPage(pageNum, pageSize);也可以使用此方式进行设置
        List<Letter> list = letterDao.queryByReceiveUid(receiveUid);;
        PageInfo<Letter> pageInfo = new PageInfo<Letter>(list);
        return Result.success(pageInfo);
    }

    //写信
    public Result<Letter> createSingleLetter(Letter newLetter) {
        try {

            //生成唯一标识lid
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            newLetter.setLid("L_" + uuid.substring(0, 10));

            //设置创建时间
            Timestamp t = new Timestamp(System.currentTimeMillis());
            String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t);
            newLetter.setCreateTime(createTime);
            addLetter(newLetter);
            return Result.success(newLetter);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

    }

}
