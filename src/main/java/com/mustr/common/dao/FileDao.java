package com.mustr.common.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.mustr.common.entity.FileBean;

@Mapper
public interface FileDao {

    Long save(FileBean file);
    
    Optional<FileBean> getById(long id);
    
    int remove(long id);
}
