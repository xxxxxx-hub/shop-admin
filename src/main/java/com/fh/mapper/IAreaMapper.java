package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.po.area.Area;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IAreaMapper extends BaseMapper<Area> {
    @Select("select id,name,pid from t_area where pid=#{id}")
    List<Area> findChilds(Long id);
}
