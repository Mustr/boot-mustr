package com.mustr.oa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mustr.oa.entity.DeptBean;

@Mapper
public interface DeptDao {

    @Select("select `id`,`parent_id`,`name`,`orderx`,`status` from TDept where id = #{id}")
    public DeptBean getDeptById(Long id);
    
    public List<DeptBean> getDepts(Map<String, Object> params);
    
    public Long save(DeptBean dept);
    
    public int update(DeptBean dept);
    
    public int remove(Long id);
    
    public int getMaxOrderx(Long parentId);
}
