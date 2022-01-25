package com.example.app.controller.letter;


import com.example.app.common.Result;
import com.example.app.entity.Letter;
import com.example.app.service.LetterService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/letter")

public class LetterController {
    @Autowired
    private LetterService letterService;


    //写信
    @PostMapping("/addLetter")
    public Result<Letter> createSingleLetter(@RequestBody Letter newLetter){
        try {
            return letterService.createSingleLetter(newLetter);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    //获取所有信件
    @PostMapping("/selectLetter")
    public Result<PageInfo<Letter>> queryByReceiveUid(
            @RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam String receiveUid){
        try {
            return letterService.queryReceiveUid(pageNum,pageSize,receiveUid);

        }catch (Exception e){

            e.printStackTrace();
            return null;
        }

    }

}
