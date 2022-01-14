package com.example.app.service;

import com.example.app.dao.ILetterDao;
import com.example.app.entity.Letter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


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

    public PageInfo queryReceiveUid(Integer pageNum, Integer pageSize ,String receiveUid)
    {
        PageHelper.startPage(pageNum, pageSize); // 设定当前页码，以及当前页显示的条数
        //PageHelper.offsetPage(pageNum, pageSize);也可以使用此方式进行设置
        List<Letter> list = letterDao.queryByReceiveUid(receiveUid);;
        PageInfo<Letter> pageInfo = new PageInfo<Letter>(list);
        return pageInfo;
    }


}
