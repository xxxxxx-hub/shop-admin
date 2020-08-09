package com.fh.biz.area;


import com.fh.common.ServerResponse;
import com.fh.mapper.IAreaMapper;
import com.fh.po.area.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("areaService")
public class IAreaServiceImpl implements IAreaService {

    @Autowired
    private IAreaMapper areaMapper;

    @Override
    public List<Area> findAreaList() {
        return areaMapper.selectList(null);
    }

    @Override
    public ServerResponse addArea(Area area) {
        areaMapper.insert(area);
        return ServerResponse.success(area.getId());
    }

    @Override
    public ServerResponse deleteArea(Long[] ids) {
        List<Long> longList = Arrays.asList(ids);
        areaMapper.deleteBatchIds(longList);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse queryAreaById(Long id) {
        Area area=   areaMapper.selectById(id);
        return ServerResponse.success(area);
    }

    @Override
    public ServerResponse updateArea(Area area) {
        areaMapper.updateById(area);
        return ServerResponse.success(area);
    }

    @Override
    public ServerResponse findChilds(Long id) {
        List <Area> areas = areaMapper.findChilds(id);
        return ServerResponse.success(areas);
    }
}
