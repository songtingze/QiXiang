package com.example.app.dao;

import com.example.app.entity.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("photoDao")
public interface IPhotoDao {
    Photo queryByPid(String pid);
    void addPhoto(Photo photo);
    void updatePhoto(Photo photo);
}
